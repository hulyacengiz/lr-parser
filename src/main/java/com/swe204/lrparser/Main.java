package com.swe204.lrparser;

import com.swe204.lrparser.model.Action;
import com.swe204.lrparser.model.GrammarRule;
import com.swe204.lrparser.service.InputReaderService;
import com.swe204.lrparser.service.LRParserService;
import com.swe204.lrparser.service.TableLoaderService;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * Entry point for the LR Parser application.
 *
 * This program:
 * 1. Loads grammar, action table, and goto table from resource files.
 * 2. Reads input tokens from a file.
 * 3. Parses the input using an LR parser.
 * 4. Writes the parsing steps and parse tree to an output file.
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Step 1: Load grammar rules and parsing tables
            TableLoaderService tableLoader = new TableLoaderService();
            List<GrammarRule> grammarRules = tableLoader.loadGrammarRules();
            Map<Integer, Map<String, Action>> actionTable = tableLoader.loadActionTable();
            Map<Integer, Map<String, Integer>> gotoTable = tableLoader.loadGotoTable();

            // Step 2: Read input tokens from file (e.g., input/input1.txt)
            InputReaderService inputReader = new InputReaderService();
            List<String> tokens = inputReader.readTokens("input3.txt");

            // Step 3: Initialize the parser with the loaded tables
            LRParserService parser = new LRParserService(grammarRules, actionTable, gotoTable);

            // Step 4: Define output file to write parser trace and parse tree
            PrintWriter writer = new PrintWriter("src/main/resources/output/output3.txt");

            // Step 5: Start parsing and write output
            parser.parse(tokens, writer);
            writer.close();

            System.out.println("Parsing completed successfully. Output written to output/output3.txt");

        } catch (Exception e) {
            e.printStackTrace(); // Print any unexpected errors
        }
    }
}