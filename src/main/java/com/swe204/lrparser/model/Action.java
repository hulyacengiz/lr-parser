package com.swe204.lrparser.model;

public class Action {
    private final String type; // "s", "r", "acc", "err"
    private final int value;

    public Action(String type, int value) {
        this.type = type;
        this.value = value;
    }

    public static Action fromString(String str) {
        if (str.equals("accept")) {
            return new Action("acc", -1);
        } else if (str.equals("-")) {
            return new Action("err", -1);
        } else {
            String type = str.substring(0, 1);
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
