
package info.freelibrary.solr;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.StringReader;

import org.apache.lucene.analysis.MockTokenizer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.BaseTokenStreamTestCase;

public class ISO639ConversionFilterTest extends BaseTokenStreamTestCase {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ISO639ConversionFilterTest.class);

    static {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        String scope = System.getProperty("solr.scope");

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Running {} test using Solr '{}'",
                    ISO639ConversionFilterTest.class.getSimpleName(), scope);
        }
    }

    @Test
    public void test() throws Exception {
        assertConvertsTo("en", new String[] {"English"});
        assertConvertsTo("eng", new String[] {"English"});
        assertConvertsTo("af", new String[] {"Afrikaans"});
        assertConvertsTo("afr", new String[] {"Afrikaans"});
    }

    static void assertConvertsTo(String aInput, String[] aExpected)
            throws Exception {
        Tokenizer tokenizer =
                new MockTokenizer(new StringReader(aInput),
                        MockTokenizer.KEYWORD, false);
        ISO639ConversionFilter filter = new ISO639ConversionFilter(tokenizer);
        assertTokenStreamContents(filter, aExpected);
    }
}
