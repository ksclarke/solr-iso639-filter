
package info.freelibrary.solr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.WebClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Test;

public class ISO639SolrIntegrationTest {

    private static final String URL = "http://localhost:8983/solr";

    private static final String SELECT = "/select/?q=*:*&indent=on";

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ISO639SolrIntegrationTest.class);

    @Test
    public void test() {
        WebClient webClient = new WebClient();

        try {
            HtmlPage page = webClient.getPage(URL + SELECT);
            LOGGER.info(page.asXml());
        } catch (Exception details) {
            fail(details.getMessage());
        }
    }

}
