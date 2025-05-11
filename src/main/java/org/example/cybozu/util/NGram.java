package org.example.cybozu.util;

import org.jetbrains.annotations.Nullable;

public class NGram {

    /**
     * ngrams are created from 1gram to this amount, currently 2grams and 3grams.
     */
    public static final int N_GRAM = 3;

    private StringBuilder grams_;
    private boolean capitalword_;

    public NGram() {
        grams_ = new StringBuilder(" ");
        capitalword_ = false;
    }

    public void addChar(char ch) {
        ch = CharNormalizer.normalize(ch);
        char lastChar = grams_.charAt(grams_.length() - 1);
        if (lastChar == ' ') {
            grams_ = new StringBuilder(" ");
            capitalword_ = false;
            if (ch==' ') return;
        } else if (grams_.length() >= N_GRAM) {
            grams_.deleteCharAt(0);
        }
        grams_.append(ch);

        if (Character.isUpperCase(ch)){
            if (Character.isUpperCase(lastChar)) capitalword_ = true;
        } else {
            capitalword_ = false;
        }
    }

    /**
     * TODO this method has some weird, undocumented behavior to ignore ngrams with upper case.
     * Get n-Gram
     * @param n length of n-gram
     * @return n-Gram String (null if it is invalid)
     */
    @Nullable
    public String get(int n) {
        if (capitalword_) return null;
        int len = grams_.length(); 
        if (n < 1 || n > N_GRAM || len < n) return null;
        if (n == 1) {
            char ch = grams_.charAt(len - 1);
            if (ch == ' ') return null;
            return Character.toString(ch);
        } else {
            return grams_.substring(len - n, len);
        }
    }

}
