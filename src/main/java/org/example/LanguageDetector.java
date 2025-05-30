package org.example;

import org.example.locale.LdLocale;
import com.google.common.base.Optional;

import java.util.List;

/**
 * Guesses the language of an input string or text.
 *
 * <p>This detector cannot handle well:
 * Short input text, can work or give wrong results.
 * Text written in multiple languages. It likely returns the language for the most prominent text. It's not made for that.
 * Text written in languages for which the detector has no profile loaded. It may just return other similar languages.
 */
public interface LanguageDetector {

    /**
     * Returns the best detected language if the algorithm is very confident.
     *
     * <p>Note: you may want to use getProbabilities() instead. This here is very strict, and sometimes returns
     * absent even though the first choice in getProbabilities() is correct.</p>
     *
     * @param text You probably want a {@link org.example.text.TextObject}.
     * @return The language if confident, absent if unknown or not confident enough.
     */
    Optional<LdLocale> detect(CharSequence text);

    /**
     * Returns all languages with at least some likeliness.
     *
     * <p>There is a configurable cutoff applied for languages with very low probability.</p>
     *
     * <p>The way the algorithm currently works, it can be that, for example, this method returns a 0.99 for
     * Danish and less than 0.01 for Norwegian, and still they have almost the same chance.</p>
     *
     * @param text You probably want a {@link org.example.text.TextObject}.
     * @return Sorted from better to worse. May be empty.
     *         It's empty if the program failed to detect any language, or if the input text did not
     *         contain any usable text (just noise).
     */
    List<DetectedLanguage> getProbabilities(CharSequence text);

    

}
