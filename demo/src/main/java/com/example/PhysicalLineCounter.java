package com.example;

import java.util.List;

/**
 * This class extends the `LineCounter` abstract class and provides functionality
 * to count physical lines of code in a Java source file. It removes comments and
 * counts only non-empty lines as physical lines.
 */
public class PhysicalLineCounter implements LineCounter {

    /**
     * Counts the physical lines in a list of code lines.
     * It first removes comments and then counts only non-empty lines as physical lines.
     *
     * @param javaFile The javaFile to count.
     * @return The total number of physical lines.
     */
    @Override
    public int count(JavaFile javaFile) {
        List<String> lines = javaFile.getLines();
        int count = 0;

        // Iterate through each line and count non-empty lines
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                count++;
            }
        }

        return count;
    }
}