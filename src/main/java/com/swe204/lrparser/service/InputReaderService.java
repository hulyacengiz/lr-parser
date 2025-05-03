package com.swe204.lrparser.service;

import com.swe204.lrparser.util.ResourceUtil;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service class responsible for reading the input tokens from a file.
 *
 * Input files are expected to be located in: src/main/resources/input/
 * and contain space-separated tokens such as:
 * id + id * id $
 */
public class InputReaderService {

    /**
     * Reads an input file and returns the tokens as a list of strings.
     *
     * @param inputFileName name of the input file (e.g., "input1.txt")
     * @return list of tokens, e.g., ["id", "+", "id", "*", "id", "$"]
     */
    public List<String> readTokens(String inputFileName) {
        List<String> tokens = new ArrayList<>();
        try (BufferedReader br = ResourceUtil.getBufferedReader("input/" + inputFileName)) {
            // Read the first line from the file
            String line = br.readLine();
            if (line != null) {
                // Split by whitespace and store tokens
                String[] parts = line.trim().split("\\s+");
                tokens.addAll(Arrays.asList(parts));
            }
        } catch (Exception e) {
            System.err.println("Failed to read input file: " + e.getMessage());
        }
        return tokens;
    }
}
