### How To Build the Solr ISO-639 Filter

This page explains how to build a version of the filter for the version of Solr that you use.  If you just want to grab a pre-built version, see the [download page](download.html).  Otherwise, you'll need to make sure you have Git and Maven (>=3) installed.  Once you've done this, you can clone the project to your machine:

    git clone https://github.com/ksclarke/solr-iso639-filter
    
After this, change into the project directory and run Maven:

    mvn install
    
This will build the filter for the latest version of Solr supported by this project, 4.4.0.

If you want to build for another version of Solr, you can supply that version as an argument to the build; for instance:

    mvn -Dsolr.version=4.2.0 install
    
or

    mvn -Dsolr.version=3.6.2 install
    
This should work for any of the supported versions of Solr (versions 3.6.1 through 4.4.0).

### Where Is the Jar File?

Once you've built the project, the packaged jar file can be found in the target directory:

    target/solr-iso639-filter-${solr.version}-r${timestamp}.jar

### For the Adventurous...

If you want to build the filter for an unsupported version of Solr, it might work, but you'll need to skip the unit tests (they're known to not work with the earlier versions of the lucene-testing-framework).  To do this, you can use:

    mvn -DskipTests=true -Dsolr.version=3.6.0 install
    
If you don't see any build errors, it might work.  I'd be interested to hear about your successes and failures.

### What's Next?

Once you've built your jar file, visit the [configuration page](configure.html "Configure the Solr ISO-69 Filter") to learn how to configure Solr to use the filter.