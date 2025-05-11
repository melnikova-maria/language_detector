package org.example.cybozu.util;

import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link LangProfile} is a Language Profile Class.
 * Users don't use this class directly.
 * Example: "foo" creates " fo" as 3gram, but not "oo ". Either this is a bug, or if intended then needs documentation.

// * @deprecated replaced by LanguageProfile
 */

public class LangProfile implements Serializable {

	@Serial
    private static final long serialVersionUID = 1L;

    /**
     * The language name (identifier).
     */
    private String name = null;

    /**
     * Key = ngram, value = count.
     * All n-grams are in here (1-gram, 2-gram, 3-gram).
     */
    private Map<String, Integer> freq = new HashMap<>();

    /**
     * Tells how many occurrences of n-grams exist per gram length.
     * When making 1grams, 2grams and 3grams (currently) then this contains 3 entries where
     * element 0 = number occurrences of 1-grams
     * element 1 = number occurrences of 2-grams
     * element 2 = number occurrences of 3-grams
     * Example: if there are 57 1-grams (English language has about that many) and the training text is
     * fairly long, then this number is in the millions.
     */
    private int[] nWords = new int[NGram.N_GRAM];

    /**
     * Constructor for JSONIC
     */
    public LangProfile() {}

    /**
     * Normal Constructor
     * @param name language name
     */
    public LangProfile(String name) {
        this.setName(name);
    }

    /**
     * Add n-gram to profile
     */
    public void add(@NotNull String gram) {
        if (name == null) throw new IllegalStateException();
        int len = gram.length();
        if (len < 1 || len > NGram.N_GRAM) {
            throw new IllegalArgumentException("ngram length must be 1-3 but was "+len+": >>>"+gram+"<<<!");
        }
        nWords[len - 1]++;
        if (freq.containsKey(gram)) {
            freq.put(gram, freq.get(gram) + 1);
        } else {
            freq.put(gram, 1);
        }
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Integer> getFreq() {
		return freq;
	}

	public void setFreq(Map<String, Integer> freq) {
		this.freq = freq;
	}

	public int[] getNWords() {
		return nWords;
	}

	public void setNWords(int[] nWords) {
		this.nWords = nWords;
	}
}
