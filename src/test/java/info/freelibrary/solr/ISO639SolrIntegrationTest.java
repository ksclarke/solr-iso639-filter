
package info.freelibrary.solr;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import java.net.URL;

import java.io.InputStream;
import java.net.URLConnection;

import nu.xom.Nodes;
import nu.xom.Document;
import nu.xom.Builder;

import org.junit.Test;

public class ISO639SolrIntegrationTest {

    private static final String QUERY =
            "http://localhost:8983/solr/select/?q=*:*&facet=true&facet.field=iso639";

    private static final String FACET_PATH = "//str[@name='facet.field']";

    /**
     * Tests that the schema registers a iso639 field and that the needed jar
     * file has been successfully loaded into Solr.
     */
    @Test
    public void test() {
        try {
            InputStream source = new URL(QUERY).openStream();
            Document doc = new Builder().build(source);
            Nodes nodes = doc.query(FACET_PATH);

            if (nodes.size() != 1) {
                fail("Didn't find the facet.field facet in the response XML");
            }

            assertEquals("iso639", nodes.get(0).getValue().trim());
        } catch (Exception details) {
            fail(details.getMessage());
        }
    }

}
