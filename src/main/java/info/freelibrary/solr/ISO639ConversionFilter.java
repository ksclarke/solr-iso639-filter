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

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates a {@link TokenFilter} that uses the {@link ISO639Converter} to convert ISO-639-1 and ISO-639-2 codes into
 * plain English.
 *
 * @author Kevin S. Clarke <ksclarke@gmail.com>
 */
public final class ISO639ConversionFilter extends TokenFilter {

    /**
     * Logger used to log warnings.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ISO639ConversionFilter.class);

    /**
     * The filter term that is a result of the conversion.
     */
    private final CharTermAttribute myTermAttribute = addAttribute(CharTermAttribute.class);

    /**
     * A Solr filter that parses ISO-639-1 and ISO-639-2 codes into English text that can be used as a facet.
     *
     * @param aStream A {@link TokenStream} that parses streams with ISO-639-1 and ISO-639-2 codes
     */
    public ISO639ConversionFilter(final TokenStream aStream) {
        super(aStream);
    }

    /**
     * Increments and processes tokens in the ISO-639 code stream.
     *
     * @return True if a value is still available for processing in the token stream; otherwise, false
     */
    @Override
    public boolean incrementToken() throws IOException {
        if (!input.incrementToken()) {
            return false;
        }

        final String t = myTermAttribute.toString();

        if (t != null && t.length() != 0) {
            try {
                myTermAttribute.setEmpty().append(ISO639Converter.convert(t));
            } catch (final IllegalArgumentException details) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn(details.getMessage(), details);
                }
            }
        }

        return true;
    }

}
