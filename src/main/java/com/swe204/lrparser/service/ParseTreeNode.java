package com.swe204.lrparser.service;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node in the parse tree.
 *
 * Each node holds a grammar symbol (either terminal or non-terminal)
 * and may have child nodes if it was created from a reduce action.
 */
public class ParseTreeNode {
    private final String symbol;                // Grammar symbol this node represents
    private final List<ParseTreeNode> children; // Child nodes (for RHS symbols)

    /**
     * Constructs a new parse tree node with the given symbol.
     * @param symbol a terminal or non-terminal from the grammar
     */
    public ParseTreeNode(String symbol) {
        this.symbol = symbol;
        this.children = new ArrayList<>();
    }

    /**
     * Adds a child node to this node.
     * Children are added in left-to-right order (matching grammar RHS).
     */
    public void addChild(ParseTreeNode child) {
        children.add(child);
    }

    public String getSymbol() {
        return symbol;
    }

    public List<ParseTreeNode> getChildren() {
        return children;
    }

    /**
     * Recursively prints all node paths from the root to each node in the tree.
     * Each line shows the full path to a node, starting from the root.
     *
     * Example:
     * /E
     * /E/E
     * /E/E/T
     * /E/E/T/F
     * /E/E/T/F/id
     */
    public void printEachNodePath(PrintWriter writer) {
        printEachNodePathRecursive(this, "", writer);
    }

    /**
     * Helper method that builds and prints the path for each node recursively.
     */
    private void printEachNodePathRecursive(ParseTreeNode node, String path, PrintWriter writer) {
        String currentPath = path + "/" + node.getSymbol();
        writer.println(currentPath);
        for (ParseTreeNode child : node.getChildren()) {
            printEachNodePathRecursive(child, currentPath, writer);
        }
    }
}
