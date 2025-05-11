package org.example.cybozu;

import com.google.common.base.Optional;
import org.example.LanguageDetector;
import org.example.LanguageDetectorBuilder;
import org.example.profiles.LanguageProfile;
import org.example.ngram.NgramExtractors;
import org.example.profiles.LanguageProfileReader;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.*;

/**
 * LangDetect Command Line Interface.

 * <p>This is a command line interface of Language Detection Library "LangDetect".</p>

 * <p>Renamed: this class was previously known as "Command".</p>
 */
public class CommandLineInterface {

    /** smoothing default parameter (ELE) */
    private static final double DEFAULT_ALPHA = 0.5;

    /** for Command line easy parser */
    private final Map<String, String> values = new HashMap<>();

    private double getParamDouble(String key, double defaultValue) {
        String value = values.get(key);
        if (value==null || value.isEmpty()) {
            return defaultValue;
        }
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid double value: >>>"+value+"<<<", e);
        }
    }

    @Nullable
    private Long getParamLongOrNull(String key) {
        String value = values.get(key);
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid long value: >>>"+value+"<<<", e);
        }
    }

    /**
     * Using all language profiles from the given directory.
     */
    public LanguageDetector makeDetector() throws IOException {
        double alpha = getParamDouble("alpha", DEFAULT_ALPHA);
        String profileDirectory = "src/main/resources/languages";
        Optional<Long> seed = Optional.fromNullable(getParamLongOrNull("seed"));

        List<LanguageProfile> languageProfiles = new LanguageProfileReader().readAll(new File(profileDirectory));

        return LanguageDetectorBuilder.create(NgramExtractors.standard())
                .alpha(alpha)
                .seed(seed)
                .shortTextAlgorithm(50)
                .withProfiles(languageProfiles)
                .build();
    }

}
