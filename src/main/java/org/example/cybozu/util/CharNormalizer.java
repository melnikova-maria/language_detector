package org.example.cybozu.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Some character normalization (and exclusion) functionality.

 * This functionality was previously embedded in the NGram class.
 */
public class CharNormalizer {

    /**
     * Character Normalization (and exclusion).
     * @return Normalized character, the space to exclude the character.
     */
    public static char normalize(char ch){
        if (ch <= 127) { //ascii (basic latin)
            if (ch < 'A' || (ch < 'a' && ch > 'Z') || ch > 'z') {
                return ' ';
            } else {
                return ch;
            }
        }

        Character result = NORMALIZE_MAP.get(ch);
        return result == null ? ch : result;
    }

    /**
     * Has mappings for overrides. What's not in the map means keep the original.
     */
    private static final Map<Character,Character> NORMALIZE_MAP = new HashMap<>();;
}
