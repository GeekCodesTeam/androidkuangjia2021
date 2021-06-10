package com.danikula.videocachedk.file;

/**
 * Generator for files to be used for caching.
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
public interface FileNameGenerator {

    String generate(String url);

}
