package com.example.validators;

/**
 * Validates whether a given line of code is a comment.
 * Supports line comments, block comments, and tracks
 * whether the validator is inside a block comment.
 */
public class CommentValidator {

    private final String LINE_COMMENT_REGEX = "^\\s*//.*";
    private final String START_BLOCK_COMMENT_REGEX = "^\\s*/\\*.*";
    private final String END_BLOCK_COMMENT_REGEX = ".*\\*/\\s*$";
    
    private boolean isInsideBlockComment = false;

    /**
     * Determines if the given line is a comment.
     * 
     * @param line The line of code to check
     * @return true if the line is a comment, false otherwise
     */
    public boolean isComment(String line) {
        if (isInsideBlockComment) {
            if (line.matches(END_BLOCK_COMMENT_REGEX)) {
                isInsideBlockComment = false;
            }
            return true;
        } else if (line.matches(START_BLOCK_COMMENT_REGEX)) {
            isInsideBlockComment = !line.matches(END_BLOCK_COMMENT_REGEX);
            return true;
        } else {
            return line.matches(LINE_COMMENT_REGEX);
        }
    }
}
