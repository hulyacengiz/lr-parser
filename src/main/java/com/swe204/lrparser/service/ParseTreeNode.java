package com.swe204.lrparser.service;

import java.util.ArrayList;
import java.util.List;

public class ParseTreeNode {
    private final String symbol;
    private final List<ParseTreeNode> children = new ArrayList<>();

    public ParseTreeNode(String symbol) {
        this.symbol = symbol;
    }

    public void addChild(ParseTreeNode child) {
        children.add(child); // Doğal sırayla ekle
    }

    public String getSymbol() {
        return symbol;
    }

    public List<ParseTreeNode> getChildren() {
        return children;
    }


    public void printEachNodePath() {
        printEachNodePathRecursive(this, "");
    }

    private void printEachNodePathRecursive(ParseTreeNode node, String path) {
        String currentPath = path + "/" + node.getSymbol();
        System.out.println(currentPath);
        for (ParseTreeNode child : node.getChildren()) {
            printEachNodePathRecursive(child, currentPath);
        }
    }
}
