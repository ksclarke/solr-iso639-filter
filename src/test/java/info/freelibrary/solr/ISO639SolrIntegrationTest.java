
package info.freelibrary.solr;

import org.custommonkey.xmlunit.XMLTestCase;

import com.gargoylesoftware.htmlunit.xml.XmlPage;
import com.gargoylesoftware.htmlunit.WebClient;

import org.junit.Test;

public class ISO639SolrIntegrationTest extends XMLTestCase {

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
            String xmlResult = page.asXml();

            assertXpathEvaluatesTo("\niso639\n", FACET_PATH, xmlResult);
        } catch (Exception details) {
            fail(details.getMessage());
        } finally {
            client.closeAllWindows();
        }
    }

}
