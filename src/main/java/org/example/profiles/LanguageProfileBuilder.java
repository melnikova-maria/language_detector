package org.example.profiles;

import org.example.locale.LdLocale;
import org.example.ngram.NgramExtractor;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Builder for {@link LanguageProfile}.
 *
 * <p>This class does no internal synchronization.</p>
 *
 * @author Fabian Kessler
 */
public class LanguageProfileBuilder {

    @NotNull
    private final LdLocale locale;
    private final int minimalFrequency = 1;
    private final Map<Integer, Map<String,Integer>> ngrams = new HashMap<>();


    public LanguageProfileBuilder(@NotNull LdLocale locale) {
        this.locale = locale;
    }

    /**
     * If the builder already has this ngram, the given frequency is added to the current count.
     */
    public LanguageProfileBuilder addGram(String ngram, int frequency) {
        Map<String, Integer> map = ngrams.get(ngram.length());
        if (map==null) {
            map = new HashMap<>();
            ngrams.put(ngram.length(), map);
        }
        Integer total = map.get(ngram);
        if (total==null) total = 0;
        total += frequency;
        map.put(ngram, total);
        return this;
    }


    public LanguageProfile build() {
        if (minimalFrequency > 1) {
            removeNgramsWithLessFrequency();
        }
        return new LanguageProfileImpl(locale, ngrams);
    }


    private void removeNgramsWithLessFrequency() {
        for (Map<String, Integer> map : ngrams.values()) {
            Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Integer> next = iterator.next();
                if (next.getValue() < minimalFrequency) {
                    iterator.remove();
                }
            }
        }
    }

}
