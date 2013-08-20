
package info.freelibrary.solr;

import java.io.StringReader;

import org.apache.lucene.analysis.MockTokenizer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.BaseTokenStreamTestCase;

import org.junit.Test;

public class ISO639ConversionFilterTest extends BaseTokenStreamTestCase {

    static {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
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
