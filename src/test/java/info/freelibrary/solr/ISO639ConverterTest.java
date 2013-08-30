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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import nu.xom.Builder;

import java.io.StringReader;

import org.junit.Test;

/**
 * Tests the ISO-639 conversion apart from the Solr/Lucene framework.
 * 
 * @author Kevin S. Clarke <ksclarke@gmail.com>
 */
public class ISO639ConverterTest {

    /**
     * Tests the conversion of two digit codes.
     */
    @Test
    public void testTwoDigitCodes() {
        assertEquals("English", ISO639Converter.convert("en"));
    }

    /**
     * Tests the conversion of three digit codes.
     */
    @Test
    public void testThreeDigitCodes() {
        assertEquals("English", ISO639Converter.convert("eng"));
    }

    /**
     * Tests the function that reports how many two digit codes are known.
     */
    @Test
    public void testTwoDigitCodeCount() {
        assertEquals(184, ISO639Converter.twoDigitCodeMapSize());
    }

    /**
     * Tests the function that reports how many three digit codes are known.
     */
    @Test
    public void testThreeDigitCodeCount() {
        assertEquals(486, ISO639Converter.threeDigitCodeMapSize());
    }

    /**
     * Tests what happens when a non-ISO-639 code is supplied.
     */
    @Test
    public void testGIGO() {
        // TODO: Is GIGO desirable? Or, what else should happen?
        assertEquals("", ISO639Converter.convert(""));
        assertEquals("Not_a_Code", ISO639Converter.convert("Not_a_Code"));
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
