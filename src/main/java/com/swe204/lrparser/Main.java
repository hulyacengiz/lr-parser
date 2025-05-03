package com.swe204.lrparser;

import com.swe204.lrparser.model.Action;
import com.swe204.lrparser.model.GrammarRule;
import com.swe204.lrparser.service.InputReaderService;
import com.swe204.lrparser.service.LRParserService;
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
        List<String> input = inputReader.readTokens("input2.txt");

        LRParserService parser = new LRParserService(grammar, actionTable, gotoTable);
        parser.parse(input);
    }
}