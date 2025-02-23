package com.example;

import java.util.List;

/**
 * This class extends the `LineCounter` abstract class and provides functionality
 * to count logical lines of code in a Java source file. It excludes comments,
 * empty lines, and certain structural elements like class/interface declarations,
 * method declarations, and control structures.
 */
public class LogicalLineCounter extends LineCounter {

    /**
     * Counts the number of logical lines in the provided list of code lines.
     * It removes comments and then evaluates each line to determine if it is a logical line.
     * Special handling is provided for `for` statements, which are counted as three logical lines.
     *
     * @param lines List of code lines to be processed.
     * @return The total number of logical lines.
     */
    @Override
    int count(List<String> lines) {
        int numLines = 0;

        // Remove comments from the lines
        List<String> cleanLines = removeComments(lines);

        // Iterate through each line and count logical lines
        for (String line : cleanLines) {
            if (isLogicalLine(line)) {
                if (isForStatement(line)) {
                    numLines += 3; // Count `for` statements as three logical lines
                } else {
                    numLines++;
                }
            }
        }

        return numLines;
    }

    /**
     * Determines if a given line is a logical line of code.
     * A line is considered logical if it is not empty, not a bracket, and not a structural element
     * like class/interface declarations, method declarations, or control structures.
     *
     * @param line The line of code to evaluate.
     * @return {@code true} if the line is a logical line, {@code false} otherwise.
     */
    private boolean isLogicalLine(String line) {
        line = line.trim();

        // Exclude lines that are not logical (e.g., empty lines, brackets, or structural elements)
        if (isEmptyOrBracket(line) || isTryStatement(line) || isClassOrInterfaceStatement(line)
                || isMethodStatement(line) || isImportStatement(line) || isPackageStatement(line)) {
            return false;
        }

        // Include lines that are logical (e.g., control structures, statements, etc.)
        if (isForStatement(line) || isSwitchCase(line) || isControlStructureWithCondition(line)
                || isControlStructureWithoutCondition(line) || isWhileStatement(line) || isBreakStatement(line)
                || isStatement(line)) {
            return true;
        }

        return false;
    }

    /**
     * Checks if a line is empty or consists solely of a bracket.
     *
     * @param line The line to check.
     * @return {@code true} if the line is empty or a bracket, {@code false} otherwise.
     */
    private boolean isEmptyOrBracket(String line) {
        return line.trim().isEmpty() || line.trim().equals("{") || line.trim().equals("}");
    }

    /**
     * Checks if a line is a class or interface declaration.
     *
     * @param line The line to check.
     * @return {@code true} if the line is a class or interface declaration, 
     * {@code false} otherwise.
     */
    private boolean isClassOrInterfaceStatement(String line) {
        return line.trim().matches(
            "^(public|private|protected|)+ *(class|interface)+.*\\{");
    }

    /**
     * Checks if a line is a method declaration.
     *
     * @param line The line to check.
     * @return {@code true} if the line is a method declaration, {@code false} otherwise.
     */
    private boolean isMethodStatement(String line) {
        return line.trim().matches(
            "^(public|private|abstract|protected)+[a-zA-Z+ _<>]+\\(+.*");
    }

    /**
     * Checks if a line is a general executable statement (ends with a semicolon).
     *
     * @param line The line to check.
     * @return {@code true} if the line is a statement, {@code false} otherwise.
     */
    private boolean isStatement(String line) {
        return line.matches(".*;$");
    }

    /**
     * Checks if a line is a `try` statement.
     *
     * @param line The line to check.
     * @return {@code true} if the line is a `try` statement, {@code false} otherwise.
     */
    private boolean isTryStatement(String line) {
        return line.trim().matches("^try+ *\\{$");
    }

    /**
     * Checks if a line is part of a control structure that specifies a condition
     * (e.g., `if`, `switch`, `catch`).
     *
     * @param line The line to check.
     * @return {@code true} if the line is a control structure with a condition, {@code false} otherwise.
     */
    private boolean isControlStructureWithCondition(String line) {
        return line.trim().matches("^(if|switch|catch)+ *\\(+.*");
    }

    /**
     * Checks if a line is part of a control structure that does not specify a condition
     * (e.g., `else`, `do`, `finally`).
     *
     * @param line The line to check.
     * @return {@code true} if the line is a control structure without a condition, {@code false} otherwise.
     */
    private boolean isControlStructureWithoutCondition(String line) {
        return line.trim().matches("^(else|do|finally)+ *\\{");
    }

    /**
     * Checks if a line is a `while` statement.
     *
     * @param line The line to check.
     * @return {@code true} if the line is a `while` statement, {@code false} otherwise.
     */
    private boolean isWhileStatement(String line) {
        return (line.trim().matches("^while+ *\\(+.*")
                || line.trim().matches("^\\}+ *while+ *\\(+.*"));
    }

    /**
     * Checks if a line is part of a `switch` structure (e.g., `case` or `default`).
     *
     * @param line The line to check.
     * @return {@code true} if the line is part of a `switch` structure, {@code false} otherwise.
     */
    private boolean isSwitchCase(String line) {
        return line.trim().matches("^\\b(case|default)\\b.*:");
    }

    /**
     * Checks if a line is a `break` statement.
     *
     * @param line The line to check.
     * @return {@code true} if the line is a `break` statement, {@code false} otherwise.
     */
    private boolean isBreakStatement(String line) {
        return line.trim().matches("^break+ *;");
    }

    /**
     * Checks if a line is a `for` statement.
     *
     * @param line The line to check.
     * @return {@code true} if the line is a `for` statement, {@code false} otherwise.
     */
    private boolean isForStatement(String line) {
        return line.trim().matches("^for+ *\\(+.*\\)+ *\\{");
    }

    /**
     * Checks if a line is an `import` statement.
     *
     * @param line The line to check.
     * @return {@code true} if the line is an `import` statement, {@code false} otherwise.
     */
    private boolean isImportStatement(String line) {
        return line.trim().matches("^import+.*;");
    }

    /**
     * Checks if a line is a `package` statement.
     *
     * @param line The line to check.
     * @return {@code true} if the line is a `package` statement, {@code false} otherwise.
     */
    private boolean isPackageStatement(String line) {
        return line.trim().matches("^package+.*;");
    }
}