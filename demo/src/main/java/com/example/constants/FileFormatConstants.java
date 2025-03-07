package com.example.constants;

/**
 * This class defines constants for file format validation messages and file types.
 * These constants are used to standardize error messages and file type identifiers
 * when validating or processing files, particularly Java source files.
 */
public class FileFormatConstants {

    /**
     * Error message indicating that a line exceeds the maximum allowed length of 120 characters.
     */
    public final static String INVALID_LINE_LENGHT_MESSAGE = "Exceeds 120 characters";

    /**
     * Error message indicating that the brace style used in the file is incorrect.
     */
    public final static String INVALID_BRACES_STYLE_MESSAGE = "Incorrect brace style";

    /**
     * Error message indicating that multiple executable statements are found where only one is allowed.
     */
    public final static String INVALID_MULTIPLE_STATEMENTS_MESSAGE = "Multiple executable statements";

    /**
     * Error message indicating that a wildcard import statement is used, which is not allowed.
     */
    public final static String INVALID_IMPORT_STATEMENTS_MESSAGE = "Contains a wildcard import";

    /**
     * Error message indicating that an annotation is formatted incorrectly.
     */
    public final static String INVALID_ANOTATION_FORMAT_MESSAGE = "Incorrect annotation formatting";

    /**
     * File extension for Java source files.
     */
    public final static String JAVA_FILE_TYPE = ".java";
}