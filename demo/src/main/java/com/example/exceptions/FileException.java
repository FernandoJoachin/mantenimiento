package com.example.exceptions;

/**
 * Exception thrown when a general file-related error occurs.
 */
public class FileException extends Exception {
    /**
     * Constructs a new FileException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public FileException(String message) {
        super(message);
    }
}