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

class DirectoryManagerTest {
    @TempDir
    Path tempDir;

    @Test
    void testIsValidDirectory_validDirectory() {
        Path tempDirectoryPath = tempDir.resolve("testDir");
        tempDirectoryPath.toFile().mkdir();

        DirectoryManager directoryManager = new DirectoryManager(tempDirectoryPath.toString());

        assertTrue(directoryManager.isValidDirectory());
    }

    @Test
    void testIsValidDirectory_invalidDirectory() {
        DirectoryManager directoryManager = new DirectoryManager("nonexistentDir");

        assertFalse(directoryManager.isValidDirectory());
    }

    @Test
    void testGetAllJavaFiles_validDirectory() throws IOException, FileException {
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

    @Test
    void testProcessDirectory_IntegrationTest() throws IOException, FileException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
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
    }
}