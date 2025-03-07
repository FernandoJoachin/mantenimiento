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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for DirectoryManager.
 * This class contains unit and integration tests for the methods in DirectoryManager.
 */
class DirectoryManagerTest {

    /**
     * Test to verify that a valid directory is recognized as such.
     * @param tempDir a temporary directory provided by JUnit for storing test files.
     */
    @Test
    public void testIsValidDirectory_validDirectory(@TempDir Path tempDir) {
        Path tempDirectoryPath = tempDir.resolve("testDir");
        tempDirectoryPath.toFile().mkdir();

        DirectoryManager directoryManager = new DirectoryManager(tempDirectoryPath.toString());

        assertTrue(directoryManager.isValidDirectory());
    }

    /**
     * Test to verify that an invalid directory is recognized as such.
     * @param tempDir a temporary directory provided by JUnit for storing test files.
     */
    @Test
    public void testIsValidDirectory_invalidDirectory(@TempDir Path tempDir) {
        DirectoryManager directoryManager = new DirectoryManager("nonexistentDir");

        assertFalse(directoryManager.isValidDirectory());
    }

    /**
     * Test to verify that all Java files in a valid directory are retrieved correctly.
     * @param tempDir a temporary directory provided by JUnit for storing test files.
     */
    @Test
    public void testGetAllJavaFiles_validDirectory(@TempDir Path tempDir) throws IOException, FileException {
        try {
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
        } catch (IOException | FileException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Integration test to verify the processing of a directory, including file analysis and output.
     * @param tempDir a temporary directory provided by JUnit for storing test files.
     */
    @Test
    public void testProcessDirectory_IntegrationTest(@TempDir Path tempDir) {
        try {
            Path tempDirectoryPath = tempDir.resolve("testDirTemp");
            tempDirectoryPath.toFile().mkdir();

            Path javaFile1 = tempDirectoryPath.resolve("Example.java");
            Files.write(
                javaFile1, 
                List.of(
                    "package com.example.files;",
                    "",
                    "public class Example {",
                    "    // Single-line comment",
                    "",
                    "    /* ",
                    "     * Multi-line comment",
                    "     * This should not be counted as a logical line",
                    "     */",
                    "",
                    "    public static void main(String[] args) {",
                    "        System.out.println(\\\"Hello, world!\\\");",
                    "    }",
                    "",
                    "    public void methodOne() {",
                    "        int x = 10;",
                    "        int y = 20;",
                    "        System.out.println(x + y);",
                    "    }",
                    "",
                    "    public int methodTwo(int a, int b) {",
                    "        return a + b;",
                    "    }",
                    "}"
                )
            );
            Path javaFile2 = tempDirectoryPath.resolve("Example2.java");
            Files.write(
                javaFile2, 
                List.of(
                    "package com.example.files;",
                    "",
                    "public class Example2 {",
                    "    public void methodOne() {",
                    "        int x = 10;",
                    "        int y = 20;",
                    "        System.out.println(x + y);",
                    "    }",
                    "",
                    "    public int methodTwo(int a, int b) {",
                    "        return a + b;",
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
            assertTrue(output.contains("| testDirTemp | 25          | 7           |"));

            Files.delete(javaFile1);
            Files.delete(javaFile2);
            Files.delete(tempDirectoryPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}