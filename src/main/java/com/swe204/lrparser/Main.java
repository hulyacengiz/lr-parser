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

            // Step 2: Prepare input reader and parser
            LRParserService parser = new LRParserService(grammarRules, actionTable, gotoTable);
            InputReaderService inputReader = new InputReaderService();

            // Step 3: Loop through all input files (input1.txt → input9.txt)
            for (int i = 1; i <= 9; i++) {
                String inputFileName = "input" + i + ".txt";
                String outputPath = "src/main/resources/output/output" + i + ".txt";

                List<String> tokens = inputReader.readTokens(inputFileName);

                if (tokens.isEmpty()) {
                    System.err.printf("Input file %s is empty or missing. Skipping.%n", inputFileName);
                    continue;
                }

                try (PrintWriter writer = new PrintWriter(outputPath)) {
                    parser.parse(tokens, writer);
                }

                System.out.printf("Parsed %s → %s%n", inputFileName, outputPath);
            }

            System.out.println("All input files have been processed successfully.");

        } catch (Exception e) {
            e.printStackTrace(); // Show any unexpected error
        }
    }
}
