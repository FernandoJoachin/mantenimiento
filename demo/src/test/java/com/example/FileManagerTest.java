package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.example.exceptions.FileException;

/**
 * Test class for FileManager.
 * This class contains unit tests to validate the functionality of the FileManager class,
 * specifically focusing on reading lines from a file.
 */
public class FileManagerTest {
    /**
     * Temporary directory used for testing. This directory is automatically created before each test
     * and deleted after the test completes. It provides a clean environment for file operations.
     */
    @TempDir
    Path tempDir;

    /**
     * Test to verify that lines are correctly read from a valid file.
     *
     * @throws IOException if an I/O error occurs while writing to or reading from the file.
     * @throws FileException if a file-related exception occurs.
     */
    @Test
    public void testReadLines_validFile() throws IOException, FileException {
        File tempFile = tempDir.resolve("TestFile.java").toFile();
        List<String> dummyLines = Arrays.asList(
            "public class TestFile {",
            "    public static void main(String[] args) {",
            "        System.out.println(\"Hello, World!\");",
            "    }",
            "}"
        );
        Files.write(tempFile.toPath(), dummyLines);

        List<String> lines = FileManager.readLines(tempFile.getAbsolutePath());

        assertEquals(5, lines.size(), "The Java file should have 5 lines.");
        assertEquals(
            "public class TestFile {", 
            lines.get(0), 
            "The first line does not match."
        );
        assertEquals(
            "    public static void main(String[] args) {", 
            lines.get(1), 
            "The second line does not match."
        );
        assertEquals(
            "        System.out.println(\"Hello, World!\");", 
            lines.get(2), 
            "The third line does not match."
        );
        assertEquals(
            "    }", 
            lines.get(3), 
            "The fourth line does not match."
        );
        assertEquals(
            "}", 
            lines.get(4), 
            "The fifth line does not match."
        );
    }

    /**
     * Test to verify that an exception is thrown when attempting to read from an invalid file.
     *
     * @throws IOException if an I/O error occurs while creating the file path.
     * @throws FileException if a file-related exception occurs.
     */
    @Test
    public void testReadLines_invalidFile() throws IOException, FileException {
        String invalidFilePath = tempDir.resolve("NonExistentFile.java").toString();
        assertThrows(FileException.class, () -> FileManager.readLines(invalidFilePath));
    }
}