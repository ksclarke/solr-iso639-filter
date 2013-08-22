
package info.freelibrary.solr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.gargoylesoftware.htmlunit.xml.XmlPage;

import com.gargoylesoftware.htmlunit.WebClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Test;

public class ISO639SolrIntegrationTest {

    private static final String URL = "http://localhost:8983/solr";

    private static final String SELECT = "/select/?q=*:*&indent=on";

    private static final String FACET = "&facet=true&facet.field=iso639";

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ISO639SolrIntegrationTest.class);

    @Test
    public void test() {
        WebClient client = new WebClient();

        try {
            XmlPage page = client.getPage(URL + SELECT + FACET);
            LOGGER.info(page.asXml());
        } catch (Exception details) {
            fail(details.getMessage());
        } finally {
            client.closeAllWindows();
        }
    }

}
