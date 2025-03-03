package com.example.validators;

import java.util.List;

import com.example.JavaFile;
import com.example.constants.FileFormatConstants;
import com.example.constants.JavaRegexConstants;
import com.example.constants.SymbolsConstants;
import com.example.exceptions.FileFormatException;

/**
 * Provides methods to validate the format of a Java source file.
 * This class ensures that Java files adhere to specific formatting rules,
 * such as correct line length, brace style, and avoiding multiple statements per line.
 */
public class FileFormatValidator {
    /**
     * Constant representing a single allowed statement per line.
     */
    public static final int NUMBER_OF_STATEMENT_PER_LINE = 1;
    public final static String JAVA_FILE_TYPE = ".java";   
    public final static int MAX_LINE_LENGTH = 120;

    /**
     * Validates the format of a given Java source file.
     * It checks if the file type is correct and if all lines follow specific formatting rules:
     * - Line length does not exceed the maximum allowed length.
     * - No multiple executable statements in a single line.
     * - Correct usage of brace style.
     *
     * @param javaFile The java file to validate.
     * @return {@code true} if the file follows the formatting rules, {@code false} otherwise.
     * @throws FileFormatException If a formatting error is detected.
     */
    public static boolean isValidFileFormat(JavaFile javaFile) throws FileFormatException {
        String fileName = javaFile.getName();
        if (!isValidFileType(fileName)) {
            return false;
        }

        List<String> lines = javaFile.getLines();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();

            if (line.isEmpty() || CommentValidator.isComment(line)) {
                continue;
            }

            line = deleteStringInsideCode(line);

            if (!isValidLineLength(line)) {
                throw new FileFormatException(
                    "Error: Line " +
                    (i + 1) +
                    " " +
                    fileName +
                    " " +
                    FileFormatConstants.INVALID_LINE_LENGHT_MESSAGE
                );
            }

            if (!isValidBracesStyle(line)) {
                throw new FileFormatException(
                    "Error: Line " +
                    (i + 1) + 
                    " " +
                    fileName + 
                    " " +
                    FileFormatConstants.INVALID_BRACES_STYLE_MESSAGE
                );
            }

            if (!isValidMultipleStatements(line)) {
                throw new FileFormatException(
                    "Error: Line " + 
                    (i + 1) + 
                    " " +
                    fileName + 
                    " " +
                    FileFormatConstants.INVALID_MULTIPLE_STATEMENTS_MESSAGE
                );
            }

            if (!isValidImportStatement(line)) {
                throw new FileFormatException(
                    "Error: Line " +
                    (i + 1) + 
                    " " +
                    fileName +
                    " " +
                    FileFormatConstants.INVALID_IMPORT_STATEMENTS_MESSAGE
                );
            }

            if (!isValidAnnotationFormat(line, i > 0 ? lines.get(i - 1).trim() : "")) {
                throw new FileFormatException(
                    "Error: Line " +
                    (i + 1) + 
                    " " + 
                    fileName + 
                    " " +
                    FileFormatConstants.INVALID_ANOTATION_FORMAT_MESSAGE
                );
            }
        }

        return true;
    }

    /**
     * Checks if the file has a valid Java file extension.
     *
     * @param fileName The name of the file to check.
     * @return {@code true} if the file is a Java file, {@code false} otherwise.
     */
    private static boolean isValidFileType(String fileName) {
        return fileName.endsWith(JAVA_FILE_TYPE);
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

        if (isValidDeclaration(line)) {
            return true;
        }

        long semicolonCount = line.chars().filter(c -> c == SymbolsConstants.SEMICOLON.hashCode()).count();

        return semicolonCount <= NUMBER_OF_STATEMENT_PER_LINE;
    }

    /**
     * Verifies if the line of code has a valid style for opening braces.
     * This means that if the line ends with an opening brace `{`, it must be preceded by * a valid declaration of a class, method, or a control structure.
     *
     * @param line The current line of code being evaluated.
     * @return {@code true} if the line is valid or does not end with an opening brace,
     *         {@code false} if it ends with an opening brace but the previous line is not a valid declaration.
     */
    private static boolean isValidBracesStyle(String line) {
        
        if (endsWithOpeningAndClosingBrace(line) || isControlStructureWithSemicolon(line)) {
            return false;
        }

        return !endsWithOpeningBrace(line) || isValidDeclaration(line);
    }

    /**
     * Removes string literals from a line of code.
     *
     * @param line The line of code.
     * @return The modified line without string literals.
     */
    private static String deleteStringInsideCode(String line) {
        return line.replaceAll(FileFormatConstants.QUOTED_STRING_REGEX, "");
    }


    /**
     * Checks if a given line ends with an opening brace "{".
     *
     * @param line the line to check
     * @return true if the line ends with "{", false otherwise
     */
    private static boolean endsWithOpeningBrace(String line) { 
        return line.endsWith(SymbolsConstants.OPENING_BRACE);
    }

    /**
     * Checks if a given line ends with an opening and closing brace "{}".
     *
     * @param line the line to check
     * @return true if the line ends with "{}", false otherwise
     */
    private static boolean endsWithOpeningAndClosingBrace(String line) {
        return line.endsWith(SymbolsConstants.OPENING_AND_CLOSING_BRACE);
    }

    /**
     * Determines if a given line represents a control structure followed by a semicolon.
     * The supported control structures are: for, while, do, switch, if.
     * Example:
     * while( condition );
     *
     * @param line the line to check
     * @return true if the line matches a control structure with a semicolon, false otherwise
     */
    private static boolean isControlStructureWithSemicolon(String line) {
        return line.trim().matches(
            JavaRegexConstants.FLOW_CONTROL_REGEX + SymbolsConstants.SEMICOLON
        );
    }

    /**
     * Validates whether a given line represents a valid declaration.
     * This includes access modifiers (public, private, protected), class, interface,
     * enum declarations, control structures, and method definitions. 
     * They must necessarily end with {
     *
     * @param line the line to check
     * @return true if the line is a valid declaration, false otherwise
     */
    private static boolean isValidDeclaration(String line) {
        return line.trim().matches(
            "(" +
            JavaRegexConstants.STRUCTURE_DECLARATION_REGEX + 
            "|" +
            JavaRegexConstants.METHOD_DECLARATION_REGEX +
            "|" +
            JavaRegexConstants.FLOW_CONTROL_REGEX +
            ")" +
            "\\" +
            SymbolsConstants.OPENING_BRACE
        );
    }

    /**
     * Checks if a line contains a wildcard import (e.g., `import java.util.*;`).
     *
     * @param line The line of code to evaluate.
     * @return {@code true} if the line does not contain a wildcard import, 
     * {@code false} otherwise.
     */
    private static boolean isValidImportStatement(String line) {
        return !line.matches(JavaRegexConstants.WILDCARD_IMPORT_REGEX);
    }

    /**
     * Checks if an annotation is correctly formatted.
     * The annotation must be on a separate line before the method, class, or field declaration.
     *
     * @param currentLine The current line being evaluated.
     * @return {@code true} if the annotation is correctly formatted, {@code false} otherwise.
     */
    private static boolean isValidAnnotationFormat(String currentLine, String previousLine) {
        return !(
            currentLine.startsWith("@") && 
            currentLine.matches(JavaRegexConstants.ACCESS_MODIFIERS_REGEX
        ));
    }
}