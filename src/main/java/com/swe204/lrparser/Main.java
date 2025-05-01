package com.swe204.lrparser;

import com.swe204.lrparser.model.Action;
import com.swe204.lrparser.model.GrammarRule;
import com.swe204.lrparser.service.InputReaderService;
import com.swe204.lrparser.service.TableLoaderService;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        TableLoaderService loader = new TableLoaderService();

        List<GrammarRule> grammar = loader.loadGrammarRules();
        Map<Integer, Map<String, Action>> actionTable = loader.loadActionTable();
        Map<Integer, Map<String, Integer>> gotoTable = loader.loadGotoTable();

        InputReaderService inputReader = new InputReaderService();
        List<String> input = inputReader.readTokens("input1.txt");

        System.out.println("Grammar:");
        grammar.forEach(System.out::println);

        System.out.println("\nFirst action table row:");
        System.out.println(actionTable.get(0));

        System.out.println("\nFirst goto table row:");
        System.out.println(gotoTable.get(0));

        System.out.println("\nInput:");
        System.out.println(input);
    }
}