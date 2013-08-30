/**
 * Copyright 2013 Kevin S. Clarke <ksclarke@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package info.freelibrary.solr;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

import java.io.InputStream;

import nu.xom.Nodes;
import nu.xom.Document;
import nu.xom.Builder;

import org.junit.Test;

/**
 * Tests the integration between the ISO-639 filter and a live Solr instance. We
 * use Travis-CI to test against all the supported versions of Solr.
 * 
 * @author Kevin S. Clarke <ksclarke@gmail.com>
 */
public class ISO639SolrIntegrationTest {

    /**
     * Logger used to output the status of the test.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ISO639SolrIntegrationTest.class);

    /**
     * The query string we throw against Solr to see if the iso639 field exists.
     */
    private static final String QUERY =
            "http://localhost:8983/solr/select/?q=*:*&facet=true&facet.field=iso639";

    /**
     * The XPath expression we use to find the facet field info in Solr's XML
     * response.
     */
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
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Error connecting to integration server", details);
            }

            fail(details.getMessage());
        }
    }

}
