
package info.freelibrary.solr;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import nu.xom.Nodes;
import nu.xom.Document;
import nu.xom.Builder;

import java.io.StringReader;

import com.gargoylesoftware.htmlunit.xml.XmlPage;
import com.gargoylesoftware.htmlunit.WebClient;

import org.junit.Test;

public class ISO639SolrIntegrationTest {

    private static final String URL = "http://localhost:8983/solr";

    private static final String SELECT = "/select/?q=*:*&indent=on";

    private static final String FACET = "&facet=true&facet.field=iso639";

    private static final String FACET_PATH = "//str[@name='facet.field']";

    /**
     * Tests that the schema registers a iso639 field and that the needed jar
     * file has been successfully loaded into Solr.
     */
    @Test
    public void test() {
        WebClient client = new WebClient();
        
        try {
            XmlPage page = client.getPage(URL + SELECT + FACET);
            StringReader reader = new StringReader(page.asXml());
            Document doc = new Builder().build(reader);
            Nodes nodes = doc.query(FACET_PATH);
            
            if (nodes.size() != 1) {
                fail("Didn't find the facet.field facet in the response XML");
            }

            assertEquals("iso639", nodes.get(0).getValue().trim());
            
        } catch (Exception details) {
            fail(details.getMessage());
        } finally {
            client.closeAllWindows();
        }
    }

}
