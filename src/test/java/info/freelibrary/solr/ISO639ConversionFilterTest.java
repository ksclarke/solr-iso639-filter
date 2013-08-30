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

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.StringReader;

import org.apache.lucene.analysis.MockTokenizer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.BaseTokenStreamTestCase;

/**
 * Tests the {@link ISO639ConversionFilter}.
 * 
 * @author Kevin S. Clarke <ksclarke@gmail.com>
 */
public class ISO639ConversionFilterTest extends BaseTokenStreamTestCase {

    /**
     * Logger used to output which version of Solr this test is testing against.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ISO639ConversionFilterTest.class);

    static {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
    }

    /**
     * Sets up the test by outputting which version of Solr it's using.
     * 
     * @throws Exception If there is trouble outputting the logging message
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        String scope = System.getProperty("solr.scope");

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Running {} test using Solr '{}'",
                    ISO639ConversionFilterTest.class.getSimpleName(), scope);
        }
    }

    /**
     * Tests that the filter returns human-friendly text versions of supplied
     * ISO-639 codes.
     * 
     * @throws Exception If there is trouble doing the conversion
     */
    @Test
    public void test() throws Exception {
        assertConvertsTo("en", new String[] {"English"});
        assertConvertsTo("eng", new String[] {"English"});
        assertConvertsTo("af", new String[] {"Afrikaans"});
        assertConvertsTo("afr", new String[] {"Afrikaans"});
    }

    /**
     * Tests that the filter returns an empty result when an empty string is
     * supplied.
     * 
     * @throws Exception If there is a problem with the conversion
     */
    @Test
    public void testEmpty() throws Exception {
        assertConvertsTo("", new String[] {});
    }

    /**
     * Tests that the filter returns what was input if it wasn't a code.
     * 
     * @throws Exception If there is a problem with the conversion
     */
    @Test
    public void testNonCode() throws Exception {
        // TODO: GIGO? What do we want to do here?
        assertConvertsTo("Gibberish", new String[] {"Gibberish"});
    }

    /**
     * Does the work of the test using a {@link MockTokenizer}.
     * 
     * @param aInput The ISO-639 two or three digit code
     * @param aExpected The expected results
     * @throws Exception If there is trouble tokenizing or converting
     */
    static void assertConvertsTo(String aInput, String[] aExpected)
            throws Exception {
        Tokenizer tokenizer =
                new MockTokenizer(new StringReader(aInput),
                        MockTokenizer.WHITESPACE, false);
        ISO639ConversionFilter filter = new ISO639ConversionFilter(tokenizer);
        assertTokenStreamContents(filter, aExpected);
        
        // TODO: Do we want to test with other tokenizers? Using WHITESPACE.
        
        /* Source documentation:
         * 
         * http://lucene.apache.org/core/3_6_2/api/test-framework/org/apache/lucene/analysis/MockTokenizer.html
         * http://lucene.apache.org/core/4_2_0/test-framework/org/apache/lucene/analysis/MockTokenizer.html
         * http://lucene.apache.org/core/4_4_0/test-framework/org/apache/lucene/analysis/MockTokenizer.html
         * 
         */
    }
}
