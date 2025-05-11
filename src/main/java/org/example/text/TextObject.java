package org.example.text;

import org.example.LanguageDetector;
import org.example.cybozu.util.CharNormalizer;
import com.google.common.annotations.Beta;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * A convenient text object implementing CharSequence and Appendable.

 * This is an ideal object to use for learning text to create {@link LanguageDetector}s,
 * as well as to pass it in to {@link LanguageDetector#detect}.

 * To get one, use a TextObjectFactory (through a TextObjectFactoryBuilder).

 * Example use:
 * //create the factory once:
 * TextObjectFactory textObjectFactory = new TextObjectFactoryBuilder()
 *     .withTextFilter(UrlTextFilter.getInstance())
 *     .build();
 * //then create as many text objects as you like:
 * TextObject inputText = textObjectFactory.create().append("deutsche Text").append(" ").append("blah blah");

 * All append() methods go through the {@code textFilter}.

 * Equals/hashCode are not implemented as of now on purpose. You may want to call toString() and compare that.
 */
@Beta
public class TextObject implements CharSequence, Appendable {

    @NotNull
    private final TextFilter textFilter;

    @NotNull
    private final StringBuilder stringBuilder;

    private final int maxTextLength;


    /**
     * @param maxTextLength 0 for no limit
     */
    public TextObject(@NotNull TextFilter textFilter, int maxTextLength) {
        this.textFilter = textFilter;
        this.maxTextLength = maxTextLength;
        this.stringBuilder = new StringBuilder();
    }

    /**
     * Append the target text for language detection.
     * If the total size of target text exceeds the limit size ,
     * the rest is cut down.
     *
     * @param text the target text to append
     */
    @Override
    public TextObject append(CharSequence text) {
        if (maxTextLength>0 && stringBuilder.length()>=maxTextLength) return this;

        text = textFilter.filter(text);

        char pre = stringBuilder.length()==0 ? 0 : stringBuilder.charAt(stringBuilder.length()-1);
        for (int i=0; i<text.length() && (maxTextLength==0 || stringBuilder.length()<maxTextLength); i++) {
            char c = CharNormalizer.normalize(text.charAt(i));
            if (c != ' ' || pre != ' ') {
                stringBuilder.append(c);
            }
            pre = c;
        }

        return this;
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
        return append(csq.subSequence(start, end));
    }

    @Override
    public Appendable append(char c) throws IOException {
        return append(Character.toString(c));
    }

    @Override
    public int length() {
        return stringBuilder.length();
    }

    @Override
    public char charAt(int index) {
        return stringBuilder.charAt(index);
    }


    @Override @NotNull
    public CharSequence subSequence(int start, int end) {
        return stringBuilder.subSequence(start, end);
    }

    @Override @NotNull
    public String toString() {
        return stringBuilder.toString(); //only correct impl, see interface CharSequence!
    }

}
