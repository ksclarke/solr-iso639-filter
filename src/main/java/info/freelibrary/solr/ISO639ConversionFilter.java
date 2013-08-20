
package info.freelibrary.solr;

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

import java.io.IOException;

public final class ISO639ConversionFilter extends TokenFilter {

    private final CharTermAttribute myTermAttribute =
            addAttribute(CharTermAttribute.class);

    public ISO639ConversionFilter(TokenStream aStream) {
        super(aStream);
    }

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
                // Okay to ignore exception
            }
        }

        return true;
    }

}
