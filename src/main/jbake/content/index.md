title=Welcome to the Solr-ISO639-Filter Project
date=2013-09-24
type=page
status=published
~~~~~~

This project gives you a Solr filter, packaged in a jar, that can be dropped into your Solr's lib directory.  Once deployed, it will take ISO-639-1 and ISO-639-2 codes in Solr fields and turn them into their English equivalents (for instance, 'en' becomes 'English' and 'afr' becomes 'Afrikaans').

The filter gives you a nice, human-friendly, way to facet on the language codes in your metadata.  It doesn't actually change the values in the stored field (if the field's data is stored), but just adds an English text version of the ISO-639 code to the index.

The idea for this project came from <a href="https://github.com/ruebot">Nick Ruest</a>.  He mentioned the idea in the #islandora IRC channel, and I thought it sounded like a good idea, so I implemented it.  It seems like a pretty easy win for folks working with Solr and metadata that uses ISO-639 codes.

<script>
xmlhttp=new XMLHttpRequest();
xmlhttp.open("GET", "http://freelibrary.info/mvnlookup.php?project=solr-iso639-filter", false);
xmlhttp.send();
$version = xmlhttp.responseText;
</script>

## Using Solr-ISO639-Filter

<br/>To use Solr-ISO639-Filter, reference it in your project's `pom.xml` file.

<pre><code>&lt;dependency&gt;
  &lt;groupId&gt;info.freelibrary&lt;/groupId&gt;
  &lt;artifactId&gt;solr-iso639-filter&lt;/artifactId&gt;
  &lt;version&gt;<script>document.write($version);</script><noscript>${version}</noscript>&lt;/version&gt;
&lt;/dependency&gt;
</code></pre>

<br/>Or, to use it with Gradle/Grails, include the following in your project's `build.gradle` file:

<pre><code>compile &apos;info.freelibrary:solr-iso639-filter:<script>
document.write($version);</script><noscript>${version}</noscript>&apos;</code></pre>
<p/>
If you want to download a pre-built jar file, visit the [download page](download.html "Download the Solr ISO-639 Filter").

## Building Solr-ISO639-Filter

<br/>If you'd like to build the project yourself, you'll need a current <a href="http://openjdk.java.net/" target="_blank">JDK</a> and <a href="https://maven.apache.org/" target="_blank">Maven</a> installed and added to your system path.  You can then download a [stable release](https://github.com/ksclarke/solr-iso639-filter/releases) or clone the project using <a href="http://git-scm.com" target="_blank">Git</a>. To clone the project, type:

    git clone https://github.com/ksclarke/solr-iso639-filter.git
    cd solr-iso639-filter

<br/>To build the project, type:

    mvn install

<br/>This will build the filter for the latest version of Solr supported by this project.

If you want to build for another version of Solr, you can supply that version as an argument to the build; for instance:

    mvn -Dsolr.version=4.2.0 install

<br/>or

    mvn -Dsolr.version=3.6.2 install

<br/>This should work for any of the supported versions of Solr (versions 3.6.1 through <script>document.write($version.split('-', 1));</script>).

To build the project's documentation, type:

    mvn javadoc:javadoc

<br/>For more information, consult the "Docs" dropdown in the navigation menu at the top of the page.
