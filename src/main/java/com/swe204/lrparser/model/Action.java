package com.swe204.lrparser.model;

/**
 * Represents a single entry in the LR parsing action table.
 * Each action corresponds to one of the following:
 * - Shift (s): move to a new state and consume a token.
 * - Reduce (r): apply a grammar rule to reduce a sequence.
 * - Accept (acc): parsing is successful.
 * - Error (err): invalid action for the given input/state pair.
 */
public class Action {
    private final String type; // "s" (shift), "r" (reduce), "acc" (accept), "err" (error)
    private final int value;   // For shift/reduce: the state or rule number. -1 for accept/error.

    /**
     * Constructs an Action object with the given type and value.
     * @param type one of "s", "r", "acc", or "err"
     * @param value state number for shift, rule number for reduce, or -1
     */
    public Action(String type, int value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Parses a string representation of an action and returns the corresponding Action object.
     * Examples:
     * - "s5" → Shift to state 5
     * - "r3" → Reduce using rule 3
     * - "accept" → Accept
     * - "-" → Error
     */
    public static Action fromString(String str) {
        if (str.equals("accept")) {
            return new Action("acc", -1);
        } else if (str.equals("-")) {
            return new Action("err", -1);
        } else {
            String type = str.substring(0, 1); // either "s" or "r"
            int value = Integer.parseInt(str.substring(1));
            return new Action(type, value);
        }
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    /**
     * Provides a human-readable string for displaying the action.
     */
    @Override
    public String toString() {
        return switch (type) {
            case "s" -> "Shift " + value;
            case "r" -> "Reduce " + value;
            case "acc" -> "Accept";
            case "err" -> "Error";
            default -> "Unknown";
        };
    }
}
