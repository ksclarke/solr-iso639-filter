### Configuring the Filter

The first step to configuring the filter is getting the jar file in the right place so that Solr can see it and load it.  Solr has a <a href="http://wiki.apache.org/solr/SolrPlugins#How_to_Load_Plugins" target="_new">wiki page</a> with some information on how to find the right plugins directory for your version of Solr.  You could also take a look at <a href="https://github.com/ksclarke/travis-solr/blob/develop/travis-solr.sh" target="_new">the script</a> I use to test the Solr ISO-639 Filter with different versions of Solr.  In it, there is a `download_and_run()` function that lists the location of the lib directory for each version of Solr that this project tests against.

Once you've put the solr-iso639-filter jar file in your Solr's lib directory, you'll need to configure Solr to use it.  To do this, add the following XML elements to your schema.xml file:

    <fieldType name="iso639Code" class="solr.TextField" omitNorms="true">
      <analyzer>
        <tokenizer class="solr.PatternTokenizerFactory" pattern="[;,]\s*"/>
        <filter class="info.freelibrary.solr.ISO639ConversionFilterFactory"/>
      </analyzer>
    </fieldType>

and

    <field name="iso639" type="iso639Code" indexed="true" multiValued="true"/>

The first will need to be added to the &lt;types&gt; element and the second to the &lt;fields&gt; element.  Where these sections are in your schema.xml file will vary depending on which version of Solr you're using.

For what it's worth, you don't have to use the "iso639" and "iso639Code" names; you can choose whatever name you'd like.  These are just illustrations.  You could also choose a different tokenizer class (since most fields with ISO-639 codes probably will just have one code per field).  After configuring the above, you can index your ISO-639 codes directly into the filter's field or you can use Solr's copyField element to copy data from another field into your newly configured ISO-639 field (if you don't want to change your indexing process).

If you have any questions, [feel free to ask](team-list.html "Contact information") and I'll be glad to try to help.