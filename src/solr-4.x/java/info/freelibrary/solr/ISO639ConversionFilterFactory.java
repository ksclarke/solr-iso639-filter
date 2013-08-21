package info.freelibrary.solr;

import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

/**
 * TokenFilterFactory that returns an ISO639ConversionFilter for Solr to use.
 * 
 * @author Kevin S. Clarke
 */
public class ISO639ConversionFilterFactory extends TokenFilterFactory {
    
    public ISO639ConversionFilterFactory(Map<String, String> aMap) {
        super(aMap);
    }

    @Override
    public ISO639ConversionFilter create(TokenStream aTokenStream) {
        return new ISO639ConversionFilter(aTokenStream);
    }

}
