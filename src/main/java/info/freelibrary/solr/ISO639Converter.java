
package info.freelibrary.solr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Converts a two or three digit language code from ISO 639-1 or 639-2 into a
 * human readable language name that can be faceted.
 * <p>
 * ISO 639-1 and ISO 639-2 language codes are taken from the <a
 * href="http://www.loc.gov/standards/iso639-2/ISO-639-2_utf-8.txt">Library of
 * Congress' list</a>.
 * </p>
 * 
 * @author Kevin S. Clarke <ksclarke@gmail.com>
 */
public class ISO639Converter {

    private static Logger LOGGER = LoggerFactory
            .getLogger(ISO639Converter.class);

    private static final String MAP_FILE = "ISO-639-2_utf-8.txt";

    private static final Map<String, String> ISO639_1_MAP;

    private static final Map<String, String> ISO639_2_MAP;

    static {
        InputStream in = ISO639Converter.class.getResourceAsStream(MAP_FILE);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        Map<String, String> iso639_1_map = new HashMap<String, String>();
        Map<String, String> iso639_2_map = new HashMap<String, String>();
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String langName = parts[3].split(";")[0];

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Loading ISO 639 entry: '{}' ({}) = {} {} {}",
                            line, parts.length, parts[0], parts[2], langName);
                }

                if (parts[0].length() > 0 && langName.length() > 0) {
                    if (iso639_2_map.put(parts[0], langName) != null) {
                        throw new RuntimeException("Source file is corrupt; " +
                                "duplicate map entry for: " + parts[0]);
                    }
                }

                if (parts[2].length() > 0 && langName.length() > 0) {
                    if (iso639_1_map.put(parts[2], langName) != null) {
                        throw new RuntimeException("Source file is corrupt; " +
                                "duplicate map entry for: " + parts[2]);
                    }
                }

                if (langName.length() <= 0) {
                    throw new RuntimeException("Source file is corrupt; " +
                            "missing a language name for " + parts[0]);
                }
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("ISO-639-1 map entries: {}", iso639_1_map.size());
                LOGGER.debug("ISO-639-2 map entries: {}", iso639_2_map.size());
            }

        } catch (IOException details) {
            throw new RuntimeException(details); // something is horribly wrong
        } finally {
            IOUtils.closeQuietly(reader);
        }

        ISO639_1_MAP = Collections.unmodifiableMap(iso639_1_map);
        ISO639_2_MAP = Collections.unmodifiableMap(iso639_2_map);
    }

    /**
     * Converts a two or three digit ISO 639 language code into a human readable
     * language name.
     * 
     * @param aCode
     * @return
     */
    public static String convert(String aCode) {
        String langName;

        switch (aCode.length()) {
            case 2:
                langName = ISO639_1_MAP.get(aCode);
                break;
            case 3:
                langName = ISO639_2_MAP.get(aCode);
                break;
            default:
                langName = aCode;
        }

        return langName;
    }

    /**
     * Returns the number of two digit language codes stored in the converter.
     * 
     * @return The number of two digit language codes
     */
    public static int twoDigitCodeMapSize() {
        return ISO639_1_MAP.size();
    }

    /**
     * Returns the number of three digit language codes stored in the converter.
     * 
     * @return The number of three digit language codes
     */
    public static int threeDigitCodeMapSize() {
        return ISO639_2_MAP.size();
    }

}
