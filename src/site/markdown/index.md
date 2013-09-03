### Introduction

This project gives you a Solr filter, packaged in a jar, that can be dropped into your Solr's lib directory.  Once deployed, it will take ISO-639-1 and ISO-639-2 codes in Solr fields and turn them into their English equivalents (for instance, 'en' becomes 'English' and 'afr' becomes 'Afrikaans').

The filter gives you a nice, human-friendly, way to facet on the language codes in your metadata.  It doesn't actually change the values in the stored field (if the field's data is stored), but just adds an English text version of the ISO-639 code to the index.

The idea for this project came from <a href="https://github.com/ruebot">Nick Ruest</a> (<a href="https://twitter.com/ruebot">@ruebot</a>).  He mentioned the idea in the #islandora IRC channel, and I thought it sounded like a good idea, so I implemented it.  It seems like a pretty easy win for folks working with Solr and metadata that uses ISO-639 codes.

### Getting Started

You can choose to download a pre-built jar file, build the project for yourself or, if you run Solr from within a Maven project, add the filter as a dependency in your pom.xml file.

* If you want to download a pre-built jar file, visit the [download page](download.html "Download the Solr ISO-639 Filter").

* If you want to build your own jar file, consult the [build instructions](build.html "Build the Solr ISO-639 Filter").

* After you have built or downloaded the filter, learn how to [configure](configure.html "Configure Solr to use Solr ISO-639 Filter") it.

_<br/>If you are interested in learning how you can run Solr from within Maven, take a look at one of my other projects, <a href="http://projects.freelibrary.info/solr-jetty-maven">solr-jetty-maven</a>.  To add a new filter to Solr with <a href="http://projects.freelibrary.info/solr-jetty-maven">solr-jetty-maven</a>, you just need to add the filter as a dependency in the pom.xml file._
