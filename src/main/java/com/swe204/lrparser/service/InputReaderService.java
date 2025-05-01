package com.swe204.lrparser.service;

import com.swe204.lrparser.util.ResourceUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputReaderService {
    public List<String> readTokens(String inputFileName) {
        List<String> tokens = new ArrayList<>();
        try (BufferedReader br = ResourceUtil.getBufferedReader("input/" + inputFileName)) {
            String line = br.readLine();
            if (line != null) {
                String[] parts = line.trim().split("\\s+");
                tokens.addAll(Arrays.asList(parts));
            }
        } catch (Exception e) {
            System.err.println("Failed to read input file: " + e.getMessage());
        }
        return tokens;
    }
}
