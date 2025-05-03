package com.swe204.lrparser.service;

import com.swe204.lrparser.model.Action;
import com.swe204.lrparser.model.GrammarRule;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.ArrayList;

/**
 * Core service class that performs LR parsing using a given grammar,
 * action table, and goto table.
 *
 * It simulates a shift-reduce parser, logs the trace of parsing steps,
 * and builds a parse tree structure.
 */
public class LRParserService {

    private final List<GrammarRule> grammarRules;
    private final Map<Integer, Map<String, Action>> actionTable;
    private final Map<Integer, Map<String, Integer>> gotoTable;

    /**
     * Constructs the parser with grammar rules and precomputed parsing tables.
     *
     * @param grammarRules list of production rules
     * @param actionTable   LR parsing action table (state × terminal → Action)
     * @param gotoTable     GOTO table for non-terminals (state × non-terminal → next state)
     */
    public LRParserService(
            List<GrammarRule> grammarRules,
            Map<Integer, Map<String, Action>> actionTable,
            Map<Integer, Map<String, Integer>> gotoTable
    ) {
        this.grammarRules = grammarRules;
        this.actionTable = actionTable;
        this.gotoTable = gotoTable;
    }

    /**
     * Parses the given list of tokens and writes output to the specified writer.
     * It also builds the parse tree during parsing.
     *
     * @param tokens list of input tokens to parse
     * @param writer output destination (e.g. file or console)
     */
    public void parse(List<String> tokens, PrintWriter writer) {
        Stack<Integer> stateStack = new Stack<>();         // holds parser states
        Stack<String> symbolStack = new Stack<>();         // holds grammar symbols (terminals and non-terminals)
        Stack<ParseTreeNode> parseStack = new Stack<>();   // builds the parse tree

        stateStack.push(0); // Start at initial state

        int position = 0;

        // Header of the parsing trace table
        writer.printf("%-40s %-40s %-30s%n", "Stack", "Input", "Action");
        writer.println("--------------------------------------------------------------------------------------------------------");

        while (true) {
            int currentState = stateStack.peek();
            String currentToken = tokens.get(position);

            // Look up the action from the parsing table
            Action action = actionTable.get(currentState).getOrDefault(currentToken, new Action("err", -1));

            switch (action.getType()) {
                case "s" -> { // Shift
                    symbolStack.push(currentToken);
                    stateStack.push(action.getValue());
                    parseStack.push(new ParseTreeNode(currentToken)); // Create a tree node for the terminal
                    printStep(stateStack, symbolStack, tokens.subList(position, tokens.size()), action, writer);
                    position++;
                }
                case "r" -> { // Reduce
                    GrammarRule rule = grammarRules.get(action.getValue() - 1);
                    int rhsLength = rule.getRightHandSide().length;

                    // Pop states and symbols for RHS
                    List<ParseTreeNode> children = new ArrayList<>();
                    for (int i = 0; i < rhsLength; i++) {
                        symbolStack.pop();
                        stateStack.pop();
                        children.add(0, parseStack.pop());
                    }

                    // Push LHS to symbol stack and GOTO state to state stack
                    String lhs = rule.getLeftHandSide();
                    int fromState = stateStack.peek();
                    symbolStack.push(lhs);
                    int gotoState = gotoTable.get(fromState).get(lhs);
                    stateStack.push(gotoState);

                    // Build parse tree node
                    ParseTreeNode parent = new ParseTreeNode(lhs);
                    for (ParseTreeNode child : children) {
                        parent.addChild(child);
                    }
                    parseStack.push(parent);

                    // Custom string for reduce actions
                    Action reduceAction = new Action("r", rule.getRuleNumber()) {
                        @Override
                        public String toString() {
                            return "Reduce " + getValue() + " (GOTO [" + fromState + ", " + lhs + "])";
                        }
                    };

                    printStep(stateStack, symbolStack, tokens.subList(position, tokens.size()), reduceAction, writer);
                }
                case "acc" -> { // Accept
                    writer.println("--------------------------------------------------------------------------------------------------------");
                    writer.println("Parse tree:");
                    if (!parseStack.isEmpty()) {
                        parseStack.peek().printEachNodePath(writer); // Print full parse tree paths
                    }
                    writer.println("ACCEPTED");
                    return;
                }
                default -> { // Error
                    printStep(stateStack, symbolStack, tokens.subList(position, tokens.size()), action, writer);
                    writer.println("ERROR");
                    return;
                }
            }
        }
    }

    /**
     * Helper method to print a single step of the parse trace table.
     */
    private void printStep(Stack<Integer> stateStack, Stack<String> symbolStack, List<String> input, Action action, PrintWriter writer) {
        String stackStr = stackToString(stateStack, symbolStack);
        String inputStr = String.join(" ", input);
        writer.printf("%-40s %-40s %-30s%n", stackStr, inputStr, action.toString());
    }

    /**
     * Combines the state and symbol stacks into a single string for display.
     */
    private String stackToString(Stack<Integer> stateStack, Stack<String> symbolStack) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stateStack.size(); i++) {
            sb.append(stateStack.get(i));
            if (i < symbolStack.size()) {
                sb.append(symbolStack.get(i));
            }
        }
        return sb.toString();
    }
}
