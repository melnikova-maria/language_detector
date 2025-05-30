package org.example;

import com.google.common.base.Optional;
import org.example.locale.LdLocale;
import org.example.ngram.NgramExtractor;
import org.example.profiles.LanguageProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Builder for {@link LanguageDetector}.
 *
 * <p>This class does no internal synchronization.</p>
 */
public class LanguageDetectorBuilder {

    private static final double ALPHA_DEFAULT = 0.5;

    @NotNull
    private final NgramExtractor ngramExtractor;

    private double alpha = ALPHA_DEFAULT;
    private Optional<Long> seed = Optional.absent();
    private int shortTextAlgorithm = 50;
    private double prefixFactor = 1.0d;
    private double suffixFactor = 1.0d;

    @Nullable
    private Map<LdLocale, Double> langWeightingMap;

    @NotNull
    private final Set<LanguageProfile> languageProfiles = new HashSet<>();
    @NotNull
    private final Set<LdLocale> langsAdded = new HashSet<>();

    public static LanguageDetectorBuilder create(@NotNull NgramExtractor ngramExtractor) {
        return new LanguageDetectorBuilder(ngramExtractor);
    }

    private LanguageDetectorBuilder(@NotNull NgramExtractor ngramExtractor) {
        this.ngramExtractor = ngramExtractor;
    }


    public LanguageDetectorBuilder alpha(double alpha) {
        if (alpha<0 || alpha>1) throw new IllegalArgumentException("alpha must be between 0 and 1, but was: "+alpha);
        this.alpha = alpha;
        return this;
    }

    public LanguageDetectorBuilder seed(@NotNull Optional<Long> seed) {
        this.seed = seed;
        return this;
    }

    /**
     * Defaults to 0, which means don't use this feature. That's the old behavior.
     */
    public LanguageDetectorBuilder shortTextAlgorithm(int shortTextAlgorithm) {
        this.shortTextAlgorithm = shortTextAlgorithm;
        return this;
    }

    /**
     * To weight n-grams that are on the left border of a word differently from n-grams
     * in the middle of words, assign a value here.

     * Affixes (prefixes and suffixes) often distinguish the specific features of languages.
     * Giving a value greater than 1.0 weights these n-grams higher. A 2.0 weights them double.

     * Defaults to 1.0, which means don't use this feature.
     * @param prefixFactor 0.0 to 10.0, a suggested value is 1.5
     */
    public LanguageDetectorBuilder prefixFactor(double prefixFactor) {
        this.prefixFactor = prefixFactor;
        return this;
    }
    /**
     * Defaults to 1.0, which means don't use this feature.
     * @param suffixFactor 0.0 to 10.0, a suggested value is 2.0
     * @see #prefixFactor(double)
     */
    public LanguageDetectorBuilder suffixFactor(double suffixFactor) {
        this.suffixFactor = suffixFactor;
        return this;
    }

    /**
     * @throws IllegalStateException if a profile for the same language was added already (must be a userland bug).
     */
    public LanguageDetectorBuilder withProfile(LanguageProfile languageProfile) throws IllegalStateException {
        if (langsAdded.contains(languageProfile.getLocale())) {
            throw new IllegalStateException("A language profile for language "+languageProfile.getLocale()+" was added already!");
        }
        for (Integer gramLength : ngramExtractor.getGramLengths()) {
            if (!languageProfile.getGramLengths().contains(gramLength)) {
                throw new IllegalArgumentException("The NgramExtractor is set to handle "+gramLength+"-grams but the given language profile for "+languageProfile.getLocale()+" does not support this!");
            }
        }
        langsAdded.add(languageProfile.getLocale());
        languageProfiles.add(languageProfile);
        return this;
    }
    /**
     * @throws IllegalStateException if a profile for the same language was added already (must be a userland bug).
     */
    public LanguageDetectorBuilder withProfiles(Iterable<LanguageProfile> languageProfiles) throws IllegalStateException {
        for (LanguageProfile languageProfile : languageProfiles) {
            withProfile(languageProfile);
        }
        return this;
    }

    /**
     * @throws IllegalStateException if no LanguageProfile was {@link #withProfile added}.
     */
    public LanguageDetector build() throws IllegalStateException {
        if (languageProfiles.isEmpty()) throw new IllegalStateException();
        double probabilityThreshold = 0.1;
        double minimalConfidence = 0.9999d;
        return new LanguageDetectorImpl(
                NgramFrequencyData.create(languageProfiles, ngramExtractor.getGramLengths()),
                alpha, seed, shortTextAlgorithm,
                prefixFactor, suffixFactor,
                probabilityThreshold, minimalConfidence,
                langWeightingMap,
                ngramExtractor
        );
    }

}
