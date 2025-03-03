package com.example.validators;

public class CommentValidator {
    private static final String LINE_COMMENT_REGEX = "//.*";
    private static final String BLOCK_COMMENT_REGEX = "/\\*[\\s\\S]*?\\*/";
    private static final String START_BLOCK_COMMENT_REGEX = "/\\*.*";
    private static final String END_BLOCK_COMMENT_REGEX = ".*\\*/";

    /**
     * Checks if the given line is a comment or part of a multi-line comment.
     *
     * @param line The string to check.
     * @return True if the line is a comment or part of a comment, false otherwise.
     */
    public static boolean isComment(String line) {
        return line.matches(LINE_COMMENT_REGEX) || 
               line.matches(BLOCK_COMMENT_REGEX) || 
               line.matches(START_BLOCK_COMMENT_REGEX) || 
               line.matches(END_BLOCK_COMMENT_REGEX);
    }
    
}
