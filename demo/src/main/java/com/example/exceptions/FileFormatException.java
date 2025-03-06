package com.example.exceptions;

/**
 * Exception thrown when a file format is invalid or unsupported.
 */
public class FileFormatException extends Exception{
    /**
     * Constructs a new FileFormatException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public FileFormatException(String message) {
        super(message);
    }
}
