# solr-iso639-filter

A Solr filter that converts ISO-639-1 and ISO-639-2 codes into human-readable text that can be used as a Solr facet.  It has been tested against Solr versions 3.6.1 through 4.4.0.

### Getting Started

You can either download a pre-built jar file, build the project for yourself or, if you run Solr from within a Maven project, add the filter as a dependency in your pom.xml file.  You can learn about each of these options, as well as how to load and configure the filter, at the <a href="http://projects.freelibrary.info/solr-iso639-filter/">project's website</a>.

### TL;DR Build Instructions

Not interested in following the link above?  You can build the project by typing:

    mvn install
    
or, for a older version of Solr (i.e., older than 4.4.0), by supplying a version number:

    mvn -Dsolr.version=3.6.2 install
    
You'll find the resulting jar file in the `target` directory.  Add that to your Solr's lib directory.

To configure the filter, add the following snippets to the appropriate places in your Solr's schema.xml file:

    <fieldType name="iso639Code" class="solr.TextField" omitNorms="true">
      <analyzer>
        <tokenizer class="solr.PatternTokenizerFactory" pattern="[;,]\s*"/>
        <filter class="info.freelibrary.solr.ISO639ConversionFilterFactory"/>
      </analyzer>
    </fieldType>
    
and

    <field name="iso639" type="iso639Code" indexed="true" multiValued="true"/>

If you don't know the appropriate places, check out the project site for more detailed information.

### Project Status

[![Build Status](https://travis-ci.org/ksclarke/solr-iso639-filter.png?branch=master)](https://travis-ci.org/ksclarke/solr-iso639-filter)

### License

Apache Software License, version 2.0

### Contact

If you have questions about solr-iso639-filter feel free to contact me, Kevin S. Clarke, at ksclarke@gmail.com or, if you encounter a problem, [file an issue](https://github.com/ksclarke/solr-iso639-filter/issues "GitHub Issue Queue") in this project's GitHub issue queue.
