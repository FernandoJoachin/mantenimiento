package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import com.example.exceptions.FileException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for DirectoryManager.
 * This class contains unit and integration tests for the methods in DirectoryManager.
 */
class DirectoryManagerTest {
    /**
     * Temporary directory used for testing. This directory is automatically created before each test
     * and deleted after the test completes. It provides a clean environment for file operations.
     */
    @TempDir
    Path tempDir;

    /**
     * Test to verify that a valid directory is recognized as such.
     */
    @Test
    public void testIsValidDirectory_validDirectory() {
        Path tempDirectoryPath = tempDir.resolve("testDir");
        tempDirectoryPath.toFile().mkdir();

        DirectoryManager directoryManager = new DirectoryManager(tempDirectoryPath.toString());

        assertTrue(directoryManager.isValidDirectory());
    }

    /**
     * Test to verify that an invalid directory is recognized as such.
     */
    @Test
    public void testIsValidDirectory_invalidDirectory() {
        DirectoryManager directoryManager = new DirectoryManager("nonexistentDir");

        assertFalse(directoryManager.isValidDirectory());
    }

    /**
     * Test to verify that all Java files in a valid directory are retrieved correctly.
     *
     * @throws IOException if an I/O error occurs.
     * @throws FileException if a file-related exception occurs.
     */
    @Test
    public void testGetAllJavaFiles_validDirectory() throws IOException, FileException {
        Path tempDirectoryPath = tempDir.resolve("testDir");
        tempDirectoryPath.toFile().mkdir();

        Path javaFile1 = tempDirectoryPath.resolve("File1.java");
        Files.write(javaFile1, List.of("public class File1 {}"));
        Path javaFile2 = tempDirectoryPath.resolve("File2.java");
        Files.write(javaFile2, List.of("public class File2 {}"));
        Path txtFile = tempDirectoryPath.resolve("File3.txt");
        Files.write(txtFile, List.of("public class File2 {}"));

        DirectoryManager directoryManager = new DirectoryManager(tempDirectoryPath.toString());

        List<JavaFile> javaFiles = directoryManager.getAllJavaFiles();

        assertEquals(2, javaFiles.size());
        assertTrue(javaFiles.stream().anyMatch(file -> file.getName().equals("File1.java")));
        assertTrue(javaFiles.stream().anyMatch(file -> file.getName().equals("File2.java")));
    }

    /**
     * Integration test to verify the processing of a directory, including file analysis and output.
     *
     * @throws IOException if an I/O error occurs.
     * @throws FileException if a file-related exception occurs.
     * @throws NoSuchFieldException if a field is not found.
     * @throws SecurityException if a security violation occurs.
     * @throws IllegalArgumentException if an illegal argument is provided.
     * @throws IllegalAccessException if access to a field is denied.
     */
    @Test
    public void testProcessDirectory_IntegrationTest() {
        try {
            Path tempDirectoryPath = tempDir.resolve("testDirTemp");
            tempDirectoryPath.toFile().mkdir();

            Path javaFile1 = tempDirectoryPath.resolve("File1.java");
            Files.write(
                javaFile1, 
                List.of(
                    "public class File1 {", 
                    "    // Comment", 
                    "    int x = 0;", "}"
                )
            );
            Path javaFile2 = tempDirectoryPath.resolve("File2.java");
            Files.write(
                javaFile2, 
                List.of(
                    "public class File2 {", 
                    "    /* Block comment */", 
                    "    void method() {", 
                    "       System.out.println(\\\"Hello, World!\\\");",
                    "    }", 
                    "}"
                )
            );

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outputStream));

            DirectoryManager directoryManager = new DirectoryManager(tempDirectoryPath.toString());
            directoryManager.processDirectory();

            System.setOut(originalOut);

            String output = outputStream.toString();
            assertTrue(output.contains("| testDirTemp | 8           | 3           |"));

            Files.delete(javaFile1);
            Files.delete(javaFile2);
            Files.delete(tempDirectoryPath);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}