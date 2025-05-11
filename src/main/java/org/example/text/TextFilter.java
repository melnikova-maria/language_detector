package org.example.text;

/**
 * Allows to filter content from a text to be ignored for the n-gram analysis.
 *
 * <p>Implementations must be immutable and stateless.</p>
 */
public interface TextFilter {

    String filter(CharSequence text);

}
