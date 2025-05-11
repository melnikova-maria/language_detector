package org.example.ngram;

/**
 * Filters out some undesired n-grams.
 * Implementations must be immutable.
 */
public interface NgramFilter {

    boolean use(String ngram);

}
