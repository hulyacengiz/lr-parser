package com.swe204.lrparser.service;

import com.swe204.lrparser.model.Action;
import com.swe204.lrparser.model.GrammarRule;
import com.swe204.lrparser.util.ResourceUtil;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.util.Map;


public class TableLoaderService {
    public List<GrammarRule> loadGrammarRules() {
        List<GrammarRule> rules = new ArrayList<>();
        try (BufferedReader br = ResourceUtil.getBufferedReader("Grammar.txt")) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ", 2);
                int ruleNumber = Integer.parseInt(parts[0]);
                String[] ruleParts = parts[1].split("->");
                String lhs = ruleParts[0].trim();
                String[] rhs = ruleParts[1].trim().split(" ");
                rules.add(new GrammarRule(ruleNumber, lhs, rhs));
            }
        } catch (Exception e) {
            System.err.println("Failed to load grammar rules: " + e.getMessage());
        }
        return rules;
    }

    public Map<Integer, Map<String, Action>> loadActionTable() {
        Map<Integer, Map<String, Action>> actionTable = new HashMap<>();

        try (BufferedReader br = ResourceUtil.getBufferedReader("ActionTable.txt")) {
            String header = br.readLine();
            String[] tokens = header.trim().split("\\s+");
            List<String> columns = new ArrayList<>(List.of(tokens).subList(1, tokens.length));

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                int state = Integer.parseInt(parts[0]);
                Map<String, Action> row = new HashMap<>();
                for (int i = 1; i < parts.length; i++) {
                    row.put(columns.get(i - 1), Action.fromString(parts[i]));
                }
                actionTable.put(state, row);
            }
        } catch (Exception e) {
            System.err.println("Failed to load action table: " + e.getMessage());
        }

        return actionTable;
    }

    public Map<Integer, Map<String, Integer>> loadGotoTable() {
        Map<Integer, Map<String, Integer>> gotoTable = new HashMap<>();

        try (BufferedReader br = ResourceUtil.getBufferedReader("GotoTable.txt")) {
            String header = br.readLine();
            String[] tokens = header.trim().split("\\s+");
            List<String> columns = new ArrayList<>(List.of(tokens).subList(1, tokens.length));

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                int state = Integer.parseInt(parts[0]);
                Map<String, Integer> row = new HashMap<>();
                for (int i = 1; i < parts.length; i++) {
                    String cell = parts[i];
                    if (!cell.equals("-")) {
                        row.put(columns.get(i - 1), Integer.parseInt(cell));
                    }
                }
                gotoTable.put(state, row);
            }
        } catch (Exception e) {
            System.err.println("Failed to load goto table: " + e.getMessage());
        }

        return gotoTable;
    }
}
