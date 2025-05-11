package org.example;

import org.example.locale.LdLocale;
import org.jetbrains.annotations.NotNull;

/**
 * Holds information about a detected language: the locale (language) and the probability.
 *
 * <p>Comparable: the "better" one comes before the worse.
 * First order by probability descending (1 to 0).
 * Then order by language ascending (a to z).</p>
 *
 * <p>This class is immutable.</p>
 */
public class DetectedLanguage implements Comparable<DetectedLanguage> {

    @NotNull
    private final LdLocale locale;
    private final double probability;

    /**
     * @param probability 0-1
     */
    public DetectedLanguage(@NotNull LdLocale locale, double probability) {
        if (probability<0d) throw new IllegalArgumentException("Probability must be >= 0 but was "+probability);
        if (probability>1d) throw new IllegalArgumentException("Probability must be <= 1 but was "+probability);
        this.locale = locale;
        this.probability = probability;
    }

    @NotNull
    public LdLocale getLocale() {
        return locale;
    }

    /**
     * @return 0-1, the higher, the better.
     */
    public double getProbability() {
        return probability;
    }

    public String toString() {
        return "DetectedLanguage [ "+ locale + ": " + probability+" ]";
    }
    public String toString_cls() {
        return locale.toString();
    }


    /**
     * See class header.
     */
    @Override
    public int compareTo(DetectedLanguage o) {
        int compare = Double.compare(o.probability, this.probability);
        if (compare!=0) return compare;
        return this.locale.toString().compareTo(o.locale.toString());
    }
}
