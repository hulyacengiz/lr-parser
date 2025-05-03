package com.swe204.lrparser.model;

/**
 * Represents a single production rule in a context-free grammar.
 * A rule has the form: A -> B C D
 * - A is the left-hand side (LHS)
 * - B, C, D are symbols on the right-hand side (RHS)
 */
public class GrammarRule {
    private final int ruleNumber;         // The rule's unique index (e.g. 1, 2, 3...)
    private final String leftHandSide;    // LHS: the non-terminal symbol being defined
    private final String[] rightHandSide; // RHS: sequence of symbols (terminals/non-terminals)

    /**
     * Constructs a grammar rule with its number, left-hand side, and right-hand side.
     * Example: GrammarRule(2, "E", ["T"]) → represents the rule: E -> T
     */
    public GrammarRule(int ruleNumber, String leftHandSide, String[] rightHandSide) {
        this.ruleNumber = ruleNumber;
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }

    public int getRuleNumber() {
        return ruleNumber;
    }

    public String getLeftHandSide() {
        return leftHandSide;
    }

    public String[] getRightHandSide() {
        return rightHandSide;
    }

    /**
     * Returns a readable string of the rule, such as: 2: E -> T
     */
    @Override
    public String toString() {
        return ruleNumber + ": " + leftHandSide + " -> " + String.join(" ", rightHandSide);
    }
}
