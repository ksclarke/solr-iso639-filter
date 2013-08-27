
package info.freelibrary.solr;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

import java.io.IOException;

public final class ISO639ConversionFilter extends TokenFilter {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ISO639ConversionFilter.class);

    private final CharTermAttribute myTermAttribute =
            addAttribute(CharTermAttribute.class);

    /**
     * A Solr filter that parses ISO-639-1 and ISO-639-2 codes into English text
     * that can be used as a facet.
     * 
     * @param aStream A <code>TokenStream</code> that parses streams with
     *        ISO-639-1 and ISO-639-2 codes
     */
    public ISO639ConversionFilter(TokenStream aStream) {
        super(aStream);
    }

    /**
     * Increments and processes tokens in the ISO-639 code stream.
     * 
     * @return True if a value is still available for processing in the token
     *         stream; otherwise, false
     */
    @Override
    public boolean incrementToken() throws IOException {
        if (!input.incrementToken()) {
            return false;
        }

        String t = myTermAttribute.toString();

        if (t != null && t.length() != 0) {
            try {
                myTermAttribute.setEmpty().append(ISO639Converter.convert(t));
            } catch (IllegalArgumentException details) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn(details.getMessage(), details);
                }
            }
        }

        return true;
    }

}
