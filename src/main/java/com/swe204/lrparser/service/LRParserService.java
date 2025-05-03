package com.swe204.lrparser.service;

import com.swe204.lrparser.model.Action;
import com.swe204.lrparser.model.GrammarRule;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class LRParserService {

    private final List<GrammarRule> grammarRules;
    private final Map<Integer, Map<String, Action>> actionTable;
    private final Map<Integer, Map<String, Integer>> gotoTable;

    public LRParserService(
            List<GrammarRule> grammarRules,
            Map<Integer, Map<String, Action>> actionTable,
            Map<Integer, Map<String, Integer>> gotoTable
    ) {
        this.grammarRules = grammarRules;
        this.actionTable = actionTable;
        this.gotoTable = gotoTable;
    }

    public void parse(List<String> tokens) {
        Stack<Integer> stateStack = new Stack<>();
        Stack<String> symbolStack = new Stack<>();
        stateStack.push(0); // start state

        int position = 0;

        System.out.printf("%-40s %-40s %-30s%n", "Stack", "Input", "Action");
        System.out.println("--------------------------------------------------------------------------------------------------------");

        while (true) {
            int currentState = stateStack.peek();
            String currentToken = tokens.get(position);
            Action action = actionTable.get(currentState).getOrDefault(currentToken, new Action("err", -1));

            switch (action.getType()) {
                case "s":
                    symbolStack.push(currentToken);
                    stateStack.push(action.getValue());

                    printStep(stateStack, symbolStack, tokens.subList(position, tokens.size()), action);
                    position++;
                    break;

                case "r":
                    GrammarRule rule = grammarRules.get(action.getValue() - 1);
                    int rhsLength = rule.getRightHandSide().length;

                    for (int i = 0; i < rhsLength; i++) {
                        symbolStack.pop();
                        stateStack.pop();
                    }

                    String lhs = rule.getLeftHandSide();
                    int fromState = stateStack.peek();
                    symbolStack.push(lhs);
                    int gotoState = gotoTable.get(fromState).get(lhs);
                    stateStack.push(gotoState);

                    Action reduceAction = new Action("r", rule.getRuleNumber()) {
                        @Override
                        public String toString() {
                            return "Reduce " + getValue() + " (GOTO [" + fromState + ", " + lhs + "])";
                        }
                    };

                    printStep(stateStack, symbolStack, tokens.subList(position, tokens.size()), reduceAction);
                    break;

                case "acc":
                    System.out.println("ACCEPTED");
                    return;

                default:
                    printStep(stateStack, symbolStack, tokens.subList(position, tokens.size()), action);
                    System.out.println("ERROR");
                    return;
            }
        }
    }

    private void printStep(Stack<Integer> stateStack, Stack<String> symbolStack, List<String> input, Action action) {
        String stackStr = stackToString(stateStack, symbolStack);
        String inputStr = String.join(" ", input);
        System.out.printf("%-40s %-40s %-30s%n", stackStr, inputStr, action.toString());
    }

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
