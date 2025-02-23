package com.example;

import java.util.List;
import java.util.Arrays;

/**
 * This abstract class provides functionality to count lines of code.
 * It includes a method to remove comments from the code and an abstract method
 * to count lines based on specific implementation logic.
 */
abstract class LineCounter {
    /**
     * Removes comments from a list of code lines.
     * This method handles both block comments (e.g., /* ... * /) 
     * and line comments (e.g., // ...).
     *
     * @param lines List of original code lines.
     * @return List of lines without comments.
     */
    protected List<String> removeComments(List<String> lines) {
        String joinedLines = String.join("\n", lines);

        // Remove block comments
        joinedLines = joinedLines.replaceAll("(?s)/\\*.*?\\*/", "");

        // Remove line comments
        joinedLines = joinedLines.replaceAll("//.*(?=\n|$)", "");

        return Arrays.asList(joinedLines.split("\n"));
    }

    /**
     * Counts the lines according to the specific implementation.
     * This method must be implemented by subclasses to define specific counting logic.
     *
     * @param lines List of code lines.
     * @return Total number of counted lines.
     */
    abstract int count(List<String> lines);
}