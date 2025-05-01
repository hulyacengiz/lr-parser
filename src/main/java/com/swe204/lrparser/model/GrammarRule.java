package com.swe204.lrparser.model;

public class GrammarRule {
    private final int ruleNumber;
    private final String leftHandSide;
    private final String[] rightHandSide;

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

    @Override
    public String toString() {
        return ruleNumber + ": " + leftHandSide + " -> " + String.join(" ", rightHandSide);
    }
}
