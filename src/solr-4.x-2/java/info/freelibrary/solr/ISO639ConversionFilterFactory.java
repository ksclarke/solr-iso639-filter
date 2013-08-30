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

import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

/**
 * An implementation of a {@link TokenFilterFactory} that returns a newly
 * created {@link ISO639ConversionFilter} for Solr to use.
 * 
 * @author Kevin S. Clarke <ksclarke@gmail.com>
 */
public class ISO639ConversionFilterFactory extends TokenFilterFactory {

    /**
     * Constructs a new filter factory.
     * 
     * @param aMap A map of initial values
     */
    public ISO639ConversionFilterFactory(Map<String, String> aMap) {
        super(aMap);
    }

    /**
     * Creates a new {@link ISOConversionFilter} from a supplied
     * {@link TokenStream}.
     * 
     * @param aTokenStream A token stream from which to create the filter
     */
    @Override
    public ISO639ConversionFilter create(TokenStream aTokenStream) {
        return new ISO639ConversionFilter(aTokenStream);
    }

}
