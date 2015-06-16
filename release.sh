#! /bin/bash

##
# A release script to work around the extra work needed to support the Solr
# versioning method this filter uses.  This script doesn't need to be used by
# anyone other than the person who is pushing the jars into the Maven repo.
#
# Usage (to release a subset or to release all possible supported versions:
#     ./release.sh 4.4.0 4.5.0 4.5.1
#     ./release.sh
#
# For the release to be able to sign the artifacts, GPG will ask for a passphrase
#
# Written by: Kevin S. Clarke <ksclarke@gmail.com>
# Created: 2013-11-11
# Last Updated: 2014-06-06
# License: Apache License, Version 2.0
#
# System Dependencies: sed, grep, mvn, git
##

# The current solr-iso639-filter release version.  Release version numbers use
# the form of rYYYYMMDD; they represent code changes in the filter itself...
RELEASE_VERSION="r20150615"

# The versions of Solr that are supported by this script
SOLR_VERSIONS="3.6.1 3.6.2 4.0.0 4.1.0 4.2.0 4.2.1 4.3.0 4.3.1 4.4.0 4.5.0 4.5.1 4.6.0 4.6.1 4.7.0 4.7.1 4.7.2 4.8.0 4.8.1 4.9.0 4.9.1 4.10.0 4.10.1 4.10.2 4.10.3 4.10.4"

# Just isolating this in case I want to use this script for another Solr filter or ?
RELEASE_ARTIFACT="solr-iso639-filter"

# This hard-coded string that allows us to swap in the right Solr version number
SV="\${solr.version}"

# Check to see if we've passed in a subset of Solr versions to use
if [ $# -eq 0 ]; then
  read -a SOLR_SUBSET <<< "$SOLR_VERSIONS"
else
  SOLR_SUBSET=("$@")
fi

# Check to make sure we're not requesting a version that isn't supported
read -a SUPPORTED_VERSIONS <<< "$SOLR_VERSIONS"
for SOLR_VERSION in ${SOLR_SUBSET[@]}
do
  FOUND=false
  for SUPPORTED_VERSION in ${SUPPORTED_VERSIONS[@]}
  do
    if [[ "$SUPPORTED_VERSION" == "$SOLR_VERSION" ]]; then
      FOUND=true
    fi
  done

  # We couldn't find the requested Solr version so we will punt on the request
  if ! $FOUND; then
    echo "Sorry, ${SOLR_VERSION} is not a supported Solr version"
    exit 1
  fi
done

if grep -q "${SV}-SNAPSHOT" pom.xml; then
  sed -i -e "s/${SV}-SNAPSHOT/${SV}-${RELEASE_VERSION}-SNAPSHOT/" pom.xml
  git commit -am "Update pom.xml's version via release script [skip ci]"
fi

# Cycle through all possible Solr versions and process the ones we want
for SOLR_VERSION in ${SOLR_VERSIONS}
do
  for VERSION in ${SOLR_SUBSET[@]}
  do
    if [[ "${VERSION}" == "${SOLR_VERSION}" ]]; then
      echo ""
      echo "Publishing ${RELEASE_ARTIFACT} version: ${SOLR_VERSION}"
      mvn -Dsolr.version=${SOLR_VERSION} -q clean deploy

      if [[ $? != 0 ]]; then
        echo "Failed to successfully build ${SOLR_VERSION}"
        exit 1
      fi

      mvn -q release:prepare -Dsolr.version=${SOLR_VERSION} \
        -Dtag=${RELEASE_ARTIFACT}-${SOLR_VERSION}-${RELEASE_VERSION} \
        -DreleaseVersion=${SV}-${RELEASE_VERSION} -Dresume=false \
        -DdevelopmentVersion=${SV}-${RELEASE_VERSION}-SNAPSHOT \
        -DscmCommentPrefix="[maven-release-plugin] [skip ci] "

      # We want to catch passphrase errors since they're subject to typos
      STATUS=$?
      if [ $STATUS -ne 0 ]; then
        LABEL=${SOLR_VERSION}-${RELEASE_VERSION}
        sed -i -e "s/${LABEL}/${SV}-SNAPSHOT/" pom.xml

        # Record the change to the pom.xml version in git so we have clean slate
        git add pom.xml
        git commit -m "Update pom.xml's version via release script [skip ci]"

        echo "There was a preparation error; please check the script's output"
        exit 1
      fi

      # We're good to publish the official release
      mvn -Dsolr.version=${SOLR_VERSION} -q release:perform \
      -DscmCommentPrefix="[maven-release-plugin] [skip ci] "

      # ${SV} is swapped out with ${SOLR_VERSION}; we need to set it back
      SOLR_STAMP=${SOLR_VERSION}-${RELEASE_VERSION}-SNAPSHOT
      sed -i -e "s/${SOLR_STAMP}/${SV}-SNAPSHOT/" pom.xml
      git commit -am "Update pom.xml's version via release script [skip ci]"
    fi
  done
done

# One last little cosmetic EOL to make the output easier to read
echo ""
