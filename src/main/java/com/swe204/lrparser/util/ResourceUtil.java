package com.swe204.lrparser.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Utility class for loading files from the project's resources directory.
 *
 * This is useful for reading files like:
 * - input1.txt
 * - Grammar.txt
 * - ActionTable.txt
 * - GotoTable.txt
 *
 * These files must be located under: src/main/resources/
 */
public class ResourceUtil {

    /**
     * Returns a BufferedReader for a file located in the resources directory.
     *
     * @param pathInResources relative path inside resources folder (e.g., "input/input1.txt")
     * @return BufferedReader to read the file line by line
     * @throws RuntimeException if the file cannot be found
     */
    public static BufferedReader getBufferedReader(String pathInResources) {
        // Load file as a stream from resources
        InputStream inputStream = ResourceUtil.class.getClassLoader().getResourceAsStream(pathInResources);

        if (inputStream == null) {
            throw new RuntimeException("File not found in resources: " + pathInResources);
        }

        // Wrap the stream in a BufferedReader for convenient reading
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
