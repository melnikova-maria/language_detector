package org.example.locale;

import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A language-detector implementation of a Locale, similar to the java.util.Locale.
 *
 * <p>It represents a IETF BCP 47 tag, but does not implement all the features. Features can be added as needed.</p>
 *
 * <p>It is constructed through the {@link #fromString} factory method. The {@link #toString()} method
 * produces a parseable and persistable string.</p>
 *
 * <p>The class is immutable.</p>
 *
 * <p>The java.util.Locale cannot be used because it has issues for historical reasons, notably the
 * script code conversion for Hebrew, Yiddish and Indonesian, and more. If one needs a Locale,
 * it is simple to create one based on this object.<br/>
 * The ICU ULocale cannot be used because a) it has issues too (for our use case) and b) we're not
 * using ICU in here [yet].</p>
 *
 * <p>This class does not perform any modifications on the input. The input is used as is, and the getters
 * return it in exactly the same way. No standardization, canonicalization, cleaning.</p>
 *
 * <p>The input is validated syntactically, but not for code existence. For example the script code must
 * be a valid ISO 15924 like "Latn" or "Cyrl", in correct case. But whether the code exists or not is not checked.
 * These code standards are not fixed, simply because regional entities like Countries can change for political
 * reasons, and languages are living entities. Therefore certain codes may exist at some point in time only
 * (be introduced late, or be deprecated or removed, or even be re-assigned another meaning).
 * It is not up to us to decide whether Kosovo is a country in 2015 or not.
 * If one needs to only work with a certain range of acceptable codes, he can validate the codes through other
 * classes that have knowledge about the codes.
 * </p>
 *
 * <p>Language: as for BCP 47, the iso 639-1 code must be used if there is one. For example "fr" for French.
 * If not, the ISO 639-3 should be used. It is highly discouraged to use 639-2.
 * Right now this class enforces a 2 or 3 char code, but this may be relaxed in the future.</p>
 *
 * <p>Script: Only ISO 15924, no discussion.</p>
 *
 * <p>Region: same as for BCP 47. That means ISO 3166-1 alpha-2 and "UN M.49".
 * I can imagine relaxing it in the future to also allow 3166-2 codes.
 * In most cases the "region" is a "country".</p>
 */
public final class LdLocale {

    @NotNull
    private final String language;

    private LdLocale(@NotNull String language) {
        this.language = language;
    }

    /**
     * @param string The output of the toString() method.
     * @return either a new or possibly a cached (immutable) instance.
     */
    @NotNull
    public static LdLocale fromString(@NotNull String string) {
        if (string==null || string.isEmpty()) throw new IllegalArgumentException("At least a language is required!");

        String language = null;
        Optional<String> script = null;
        Optional<String> region = null;

        List<String> strings = Splitter.on('-').splitToList(string);
        for (int i=0; i<strings.size(); i++) {
            String chunk = strings.get(i);
            if (i==0) {
                language = assignLang(chunk);
            } else {
                if (script == null && region == null && looksLikeScriptCode(chunk)) {
                    script = Optional.of(chunk);
                } else if (region==null && (looksLikeGeoCode3166_1(chunk) || looksLikeGeoCodeNumeric(chunk))) {
                    region = Optional.of(chunk);
                } else {
                    throw new IllegalArgumentException("Unknown part: >>>"+chunk+"<<<!");
                }
            }
        }
        assert language != null;

        return new LdLocale(language);
    }

    private static boolean looksLikeScriptCode(String string) {
        return string.length() == 4 && string.matches("[A-Z][a-z]{3}");
    }

    private static boolean looksLikeGeoCode3166_1(String string) {
        return string.length()==2 && string.matches("[A-Z]{2}");
    }
    private static boolean looksLikeGeoCodeNumeric(String string) {
        return string.length()==3 && string.matches("[0-9]{3}");
    }

    private static String assignLang(String s) {
        if (!s.matches("[a-z]{2,3}")) throw new IllegalArgumentException("Invalid language code syntax: >>>"+s+"<<<!");
        return s;
    }

    /**
     * The output of this can be fed to the fromString() method.
     * @return for example "de" or "de-Latn" or "de-CH" or "de-Latn-CH", see class header.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(language);
        return sb.toString();
    }


    /**
     * @return ISO 639-1 or 639-3 language code, eg "fr" or "gsw", see class header.
     */
    @NotNull
    public String getLanguage() {
        return language;
    }
}
