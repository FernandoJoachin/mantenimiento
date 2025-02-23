package com.example;

import java.util.List;

/**
 * This class extends the `LineCounter` abstract class and provides functionality
 * to count physical lines of code in a Java source file. It removes comments and
 * counts only non-empty lines as physical lines.
 */
public class PhysicalLineCounter extends LineCounter {

    /**
     * Counts the physical lines in a list of code lines.
     * It first removes comments and then counts only non-empty lines as physical lines.
     *
     * @param lines List of code lines to be processed.
     * @return The total number of physical lines.
     */
    @Override
    int count(List<String> lines) {
        // Remove comments from the lines
        List<String> linesWithoutComments = removeComments(lines);

        int count = 0;

        // Iterate through each line and count non-empty lines
        for (String line : linesWithoutComments) {
            if (!line.trim().isEmpty()) {
                count++;
            }
        }

        return count;
    }
}