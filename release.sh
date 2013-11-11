#! /bin/bash

##
# A release script to work around the extra work needed to support the Solr
# versioning method this filter uses.  This script doesn't need to be used by
# anyone other than the person who is pushing the jars into the Maven repo.
#
# Usage (to release a subset or to release all possible supported versions:
#     ./release 4.4.0 4.5.0 4.5.1
#     ./release
#
# For the release to be able to sign the artifacts, GPG will ask for a passphrase
#
# Written by: Kevin S. Clarke <ksclarke@gmail.com>
# Created: 2013-11-11
# Last Updated: 2013-11-11
# License: Apache License, Version 2.0
#
# System Dependencies: sed, grep, mvn, git
##

# The current solr-iso639-filter release version.  Release version numbers use
# the form of rYYYYMMDD; they represent code changes in the filter itself...
RELEASE_VERSION="r20130829"

# The versions of Solr that are supported by this script
SOLR_VERSIONS="3.6.1 3.6.2 4.0.0 4.1.0 4.2.0 4.2.1 4.3.0 4.3.1 4.4.0 4.5.0 4.5.1"

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

  # We couldn't find the requested Solr version so let's punt on the request
  if ! $FOUND; then
    echo "Sorry, ${SOLR_VERSION} is not a supported Solr version"
    exit 1
  fi
done

# If we're running this on the 'develop' branch, we need to twiddle the pom.xml
# Really this should only be done for testing; releases should come from 'master'
if grep -q "${SV}-SNAPSHOT" pom.xml; then
  DEV_BRANCH=true
  sed -i -e "s/${SV}-SNAPSHOT/${SV}-${RELEASE_VERSION}-SNAPSHOT/" pom.xml
  git commit -am "temporarily changing pom.xml's version via release script"
fi

# Output a little reminder to delete any artifacts created through testing
if grep -q "${SV}-${RELEASE_VERSION}-SNAPSHOT" pom.xml; then
  echo "Running on the 'develop' branch -- PLEASE DELETE ALL ARTIFACTS!"
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
      mvn -Dsolr.version=${SOLR_VERSION} -q release:prepare \
        -Dtag=${RELEASE_ARTIFACT}-${SOLR_VERSION}-${RELEASE_VERSION} \
        -DreleaseVersion="${SV}-${RELEASE_VERSION}" -Dresume=false \
        -DdevelopmentVersion="${SV}-${RELEASE_VERSION}-SNAPSHOT"
      mvn -Dsolr.version=${SOLR_VERSION} -q release:perform
    fi
  done
done

# If on the 'develop' branch, put the pom.xml's version back the way it should be
if $DEV_BRANCH; then
  sed -i -e "s/${SV}-${RELEASE_VERSION}-SNAPSHOT/${SV}-SNAPSHOT/" pom.xml
fi

# One last little cosmetic EOL to make the output easier to read
echo ""
