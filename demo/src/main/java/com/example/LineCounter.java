package com.example;

import java.util.List;

/**
 * This interface defines the contract for counting lines of code.
 * It includes a method to count lines based on specific implementation logic.
 */
public interface LineCounter {

    /**
     * Counts the lines according to the specific implementation.
     * This method must be implemented by classes that implement this interface
     * to define specific counting logic.
     *
     * @param javaFile The javaFile to count.
     * @return Total number of counted lines.
     */
    public int count(JavaFile javaFile);
}