
package info.freelibrary.solr;

import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.solr.analysis.BaseTokenFilterFactory;

/**
 * TokenFilterFactory that returns an ISO639ConversionFilter for Solr to use.
 * 
 * @author Kevin S. Clarke
 */
public class ISO639ConversionFilterFactory extends BaseTokenFilterFactory {

    public ISO639ConversionFilterFactory() {
        super();
    }

    @Override
    public void init(Map<String,String> args) {
        super.init(args);
        assureMatchVersion();
    }
    
    @Override
    public ISO639ConversionFilter create(TokenStream aTokenStream) {
        return new ISO639ConversionFilter(aTokenStream);
    }

}
