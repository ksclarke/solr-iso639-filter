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
import org.apache.solr.analysis.BaseTokenFilterFactory;

/**
 * {@link TokenFilterFactory} that returns an {@link ISO639ConversionFilter} for
 * Solr to use.
 * 
 * @author Kevin S. Clarke <ksclarke@gmail.com>
 */
public class ISO639ConversionFilterFactory extends BaseTokenFilterFactory {

    /**
     * Constructs a new filter factory.
     */
    public ISO639ConversionFilterFactory() {
        super();
    }

    /**
     * Initializes a new filter factory.
     * 
     * @param aMap A map of initial values
     */
    @Override
    public void init(final Map<String, String> aMap) {
        super.init(aMap);
        assureMatchVersion();
    }

    /**
     * Creates a new filter from the supplied {@link TokenStream}.
     * 
     * @param aTokenStream A token stream from which to create the filter
     * @return A newly created {@link ISO639ConversionFilter}
     */
    @Override
    public ISO639ConversionFilter create(final TokenStream aTokenStream) {
        return new ISO639ConversionFilter(aTokenStream);
    }

}
