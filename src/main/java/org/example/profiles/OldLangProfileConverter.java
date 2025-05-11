package org.example.profiles;

import org.example.cybozu.util.LangProfile;
import org.example.locale.LdLocale;

import java.util.Map;

/**
 * Converts an old {@link LangProfile} to a new {@link LanguageProfile}.
 */
public class OldLangProfileConverter {

    public static LanguageProfile convert(LangProfile langProfile) {
        LdLocale locale;
        try {
            locale = LdLocale.fromString(langProfile.getName());
        } catch (Exception e) {
            throw new RuntimeException("Profile file name logic was changed in v0.5, please update your custom profiles!", e);
        }
        LanguageProfileBuilder builder = new LanguageProfileBuilder(locale);
        for (Map.Entry<String, Integer> entry : langProfile.getFreq().entrySet()) {
            builder.addGram(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

}
