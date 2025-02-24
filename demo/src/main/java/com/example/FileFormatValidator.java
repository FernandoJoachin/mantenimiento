package com.example;

import java.util.List;

/**
 * This class provides methods to validate the format of a Java source file.
 * It checks various formatting rules such as line length, brace style, and multiple executable statements.
 */
public class FileFormatValidator {
    private static final int MAX_LINE_LENGTH = 120;

    /**
     * Validates the format of a given Java source file.
     * It checks if the file type is correct and if all lines follow specific formatting rules:
     * - Line length does not exceed the maximum allowed length.
     * - No multiple executable statements in a single line.
     * - Correct usage of brace style.
     *
     * @param fileName The name of the file to validate.
     * @param lines    The content of the file, represented as a list of lines.
     * @return {@code true} if the file follows the formatting rules, {@code false} otherwise.
     */
    public static boolean isValidFileFormat(String fileName, List<String> lines) {
        if (!isValidFileType(fileName)) {
            System.out.println("Error: Invalid file type -> " + fileName);
            return false;
        }

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();

            if (line.isEmpty()) {
                continue;
            }

            if (!isValidLineLength(line)) {
                System.out.println("Warning: Line " + (i + 1) + " in file " + fileName 
                    + " exceeds 120 characters.");
                return false;
            }

            if (!isValidBracesStyle(line)) {
                System.out.println("Error: Line " + (i + 1) + " in file " + fileName 
                    + " has incorrect brace style.");
                return false;
            }

            if (!isValidMultipleStatements(line)) {
                System.out.println("Error: Line " + (i + 1) + " in file " + fileName 
                    + " contains multiple executable statements.");
                return false;
            }

            if (!isValidImportStatement(line)) {
                System.out.println("Error: Line " + (i + 1) + " in file " + fileName 
                    + " contains a wildcard import.");
                return false;
            }

            if (!isValidAnnotationFormat(line, i > 0 ? lines.get(i - 1).trim() : "")) {
                System.out.println("Error: Line " + (i + 1) + " in file " + fileName 
                    + " has incorrect annotation formatting.");
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the provided file has a valid Java file extension.
     *
     * @param fileName The name of the file to check.
     * @return {@code true} if the file is a Java file, {@code false} otherwise.
     */
    private static boolean isValidFileType(String fileName) {
        return fileName.endsWith(".java");
    }

    /**
     * Checks if a given line does not exceed the maximum allowed length.
     *
     * @param line The line of code to check.
     * @return {@code true} if the line length is within the allowed limit, {@code false} otherwise.
     */
    private static boolean isValidLineLength(String line) {
        return line.length() <= MAX_LINE_LENGTH;
    }

    /**
     * Checks if a line of code contains multiple executable statements.
     * The `for` loop structure is allowed since it may contain multiple `;`
     * without being considered separate statements.
     *
     * @param line The line of code to evaluate.
     * @return {@code true} if the line does not contain multiple statements, {@code false} if it does.
     */
    private static boolean isValidMultipleStatements(String line) {
        boolean isValidForLoopStructure = line.matches(
            "\\s*for\\s*\\(.*;.*;.*\\)\\s*\\{?\\s*");

        if (isValidForLoopStructure) {
            return true;
        }

        String stringWithoutQuotes = line.replaceAll("\"[^\"]*\"", "");
        long semicolonCount = stringWithoutQuotes.chars().filter(c -> c == ';').count();

        return semicolonCount <= 1;
    }

    /**
     * Verifies if the line of code has a valid style for opening braces.
     * This means that if the line ends with an opening brace `{`, it must be preceded by a valid
     * declaration of a class, method, or a control structure.
     *
     * @param line The current line of code being evaluated.
     * @return {@code true} if the line is valid or does not end with an opening brace,
     *         {@code false} if it ends with an opening brace but the previous line is not a valid declaration.
     */
    private static boolean isValidBracesStyle(String line) {
        boolean endsWithOpeningBrace = line.endsWith("{");
        boolean endsWithOpeningAndClosingBrace = line.endsWith("{}");
        // Checks if the line is a control structure (for, while, do, switch, if) followed by a semicolon `;`
        // This prevents misinterpretation of single-line statements.
        boolean isControlStructureWithSemicolon = line.trim().matches(
            "\\s*(for|while|do|switch|if)\\s*\\(.*\\)\\s*;\\s*");
        // Checks if the line contains a valid declaration or method signature followed by `{`
        // This includes class, interface, enum, access modifiers (public, private, protected),
        // and control structures (if, else, for, while, switch, do).
        boolean isValidDeclarationOrMethod = line.trim().matches(
            ".*\\s*(public|private|protected|class|interface|enum|if|else|for|while|switch|do)\\s+.*\\{.*|.*\\)\\s*\\{.*");

        if (endsWithOpeningAndClosingBrace || isControlStructureWithSemicolon) {
            return false;
        }

        if (!endsWithOpeningBrace) {
            return true;
        }

        if (!isValidDeclarationOrMethod) {
            return false;
        }

        return true;
    }

    /**
     * Checks if a line contains a wildcard import (e.g., `import java.util.*;`).
     *
     * @param line The line of code to evaluate.
     * @return {@code true} if the line does not contain a wildcard import, 
     * {@code false} otherwise.
     */
    private static boolean isValidImportStatement(String line) {
        if (line.startsWith("import")) {
            return !line.contains(".*;");
        }
        return true;
    }

    /**
     * Checks if an annotation is correctly formatted.
     * The annotation must be on a separate line before the method, class, or field declaration.
     *
     * @param currentLine The current line being evaluated.
     * @param previousLine The previous line in the file.
     * @return {@code true} if the annotation is correctly formatted, {@code false} otherwise.
     */
    private static boolean isValidAnnotationFormat(String currentLine, String previousLine) {
        if (currentLine.startsWith("@")) {
            if (!previousLine.isEmpty()) {
                return false;
            }

            // Check if the annotation is on the same line as a declaration (public, private, etc.)
            if (currentLine.matches(".*\\s+(public|private|protected|class|interface|enum|void|int|String|boolean).*")) {
                return false;
            }
        }
        return true;
    }
}