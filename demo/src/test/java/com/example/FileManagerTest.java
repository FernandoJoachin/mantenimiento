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
     * Test to verify that lines are correctly read from a valid file.
     * @param tempDir a temporary directory provided by JUnit for storing test files.
     */
    @Test
    public void testReadLines_validFile(@TempDir Path tempDir) {
        try {
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
        } catch (IOException | FileException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test to verify that an exception is thrown when attempting to read from an invalid file.
     * @param tempDir a temporary directory provided by JUnit for storing test files.
     */
    @Test
    public void testReadLines_invalidFile(@TempDir Path tempDir) {
        try {
            String invalidFilePath = tempDir.resolve("NonExistentFile.java").toString();
            assertThrows(FileException.class, () -> FileManager.readLines(invalidFilePath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}