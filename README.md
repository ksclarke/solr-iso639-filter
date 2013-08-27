# solr-iso639-filter

A Solr filter that converts ISO-639-1 and ISO-639-2 codes into human-readable text that can be used as a Solr facet.  It has been tested against Solr versions 3.6.1 through 4.4.0.

### Getting Started

solr-iso639-filter uses [Maven](http://maven.apache.org/) to compile and package the filter into a jar file, so you'll need to have Maven (>= 3) installed.  After that, you can check the project out with Git:

    git clone https://github.com/ksclarke/solr-iso639-filter

Once you've done this, change into the project directory and run the following command:

    mvn install

This will build the filter for Solr 4.4.0.  If you want to build for another version of Solr you can supply that version as an argument to the build; for instance:

    mvn -Dsolr.version=4.2.0 install

or

    mvn -Dsolr.version=3.6.2 install

>**Pro Tip:** If you want to build the filter against an earlier version of Solr than is supported by the project, tell Maven to skip the tests (e.g.  `mvn -Dsolr.version=3.5.0 -DskipTests=true install`). The filter itself might work, but this project's unit tests are known to fail with versions of the lucene-test-framework older than 3.6.1.

Once you've built the project, the packaged jar file can be found in the target directory:
 
    target/solr-iso639-filter-${solr.version}.1-SNAPSHOT.jar
    
You'll need to put this jar in the [lib directory](http://wiki.apache.org/solr/SolrPlugins "Solr plugin lib directory details") that your Solr instance uses for plugins.  Once you've done that, you'll need to configure the filter in your schema.xml file.  This will involve defining a field type and a field that uses that type.  You'll add each to their relevant locations in the schema.xml file; for instance, you'll add something like the following to the &lt;types&gt; element:

    <fieldType name="iso639Code" class="solr.TextField" omitNorms="true">
      <analyzer>
        <tokenizer class="solr.PatternTokenizerFactory" pattern="[;,]\s*"/>
        <filter class="info.freelibrary.solr.ISO639ConversionFilterFactory"/>
      </analyzer>
    </fieldType>
    
And, you'll add something like the following to the &lt;fields&gt; element:

    <field name="iso639" type="iso639Code" indexed="true" multiValued="true"/>
    
You'll then need to restart Tomcat (or whatever is running your Solr instance).  Once you've done this, you can index data into the newly configured field and the ISO639-1 / ISO639-2 codes will be translated, in the index, into plain English.

You can see this by submitting a test query to your Solr instance... something like:

http://localhost:8983/solr/collection1/select?q=RECID&wt=json&indent=true&facet=true&facet.field=iso639

If you've added an 'en' code to your record's iso639 field, for instance, you should get back JSON that contains:

    "facet_counts": {
      "facet_queries": {},
      "facet_fields": {
        "iso639": [
          "English",
          1
        ]
      },
      "facet_dates": {},
      "facet_ranges": {}
    }

### Project Status

[![Build Status](https://travis-ci.org/ksclarke/solr-iso639-filter.png?branch=master)](https://travis-ci.org/ksclarke/solr-iso639-filter)

### License

Apache Software License, version 2.0

### Contact

If you have questions about solr-iso639-filter feel free to contact me, Kevin S. Clarke, at ksclarke@gmail.com

Alternatively, you can [file an issue](https://github.com/ksclarke/solr-iso639-filter/issues "GitHub Issue Queue") in this project's GitHub issue queue if you encounter any problems.
