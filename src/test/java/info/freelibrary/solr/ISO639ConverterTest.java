
package info.freelibrary.solr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import nu.xom.Builder;

import java.io.StringReader;

import org.junit.Test;

public class ISO639ConverterTest {

    @Test
    public void testTwoDigitCodes() {
        assertEquals("English", ISO639Converter.convert("en"));
    }

    @Test
    public void testThreeDigitCodes() {
        assertEquals("English", ISO639Converter.convert("eng"));
    }

    @Test
    public void testTwoDigitCodeCount() {
        assertEquals(184, ISO639Converter.twoDigitCodeMapSize());
    }

    @Test
    public void testThreeDigitCodeCount() {
        assertEquals(486, ISO639Converter.threeDigitCodeMapSize());
    }

    /**
     * Tests to make sure that the hand-built XML from toString() is actually
     * well-formed. Putting this check in a test saves me from having to make
     * XOM a dependency of the filter.
     */
    @Test
    public void testToString() {
        String xml = new ISO639Converter().toString();
        StringReader reader = new StringReader(xml);

        try {
            new Builder().build(reader);
        } catch (Exception details) {
            fail("XML from toString() isn't well-formed: " + details);
        }
    }
}
