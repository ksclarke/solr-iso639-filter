
package info.freelibrary.solr;

import static org.junit.Assert.assertEquals;

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
}
