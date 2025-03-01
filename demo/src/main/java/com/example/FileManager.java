package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.exceptions.FileException;

/**
 * The FileManager class handles file reading operations and provides functionality
 * to list file names in a directory. It also includes utility methods to remove
 * comments from code lines.
 */
class FileManager {

    /**
     * Reads all lines from a specified file.
     *
     * @param filePath The full path of the file to read.
     * @return A list containing the file's lines. If the file does not exist,
     *         an exception is thrown.
     * @throws IOException If an error occurs while reading the file.
     * @throws FileException If the file does not exist or is not valid.
     */
    public static List<String> readLines(String filePath) throws IOException, FileException {
        File file = new File(filePath);
        if (!isValidFile(file)) {
            throw new FileException("The file does not exist: " + file.getName());
        }

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return removeComments(lines);
    }

    /**
     * Checks if the specified file is valid.
     * A file is considered valid if it exists and is indeed a file.
     *
     * @param file The file to validate.
     * @return {@code true} if the file exists and is a valid file;
     *         {@code false} otherwise.
     */
    private static boolean isValidFile(File file) {
        if (!file.exists() || !file.isFile()) {
            return false;
        }
        return true;
    }

    /**
     * Removes comments from a list of code lines.
     * This method handles both block comments (e.g., /* ... * /)
     * and line comments (e.g., // ...).
     *
     * @param lines List of original code lines.
     * @return List of lines without comments.
     */
    private static List<String> removeComments(List<String> lines) {
        String joinedLines = String.join("\n", lines);

        // Remove block comments
        joinedLines = joinedLines.replaceAll("(?s)/\\*.*?\\*/", "");

        // Remove line comments
        joinedLines = joinedLines.replaceAll("//.*(?=\n|$)", "");

        return Arrays.asList(joinedLines.split("\n"));
    }
}