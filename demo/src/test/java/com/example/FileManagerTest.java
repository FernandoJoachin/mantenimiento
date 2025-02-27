package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileManagerTest {

    private FileManager fileManager;

    @BeforeEach
    public void setUp(@TempDir Path tempDir) throws IOException {
        fileManager = new FileManager(tempDir.toString());
    }

    @Test
    public void testReadLinesWithValidJavaFile(@TempDir Path tempDir) throws IOException {
        // Crear archivo temporal con código Java
        File tempFile = tempDir.resolve("TestFile.java").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("public class TestFile {");
            writer.newLine();
            writer.write("    public static void main(String[] args) {");
            writer.newLine();
            writer.write("        System.out.println(\"Hello, World!\");");
            writer.newLine();
            writer.write("    }");
            writer.newLine();
            writer.write("}");
        }

        List<String> lines = fileManager.readLines(tempFile.getAbsolutePath());

        assertEquals(5, lines.size(), "The Java file should have 5 lines.");
        assertEquals("public class TestFile {", lines.get(0), "The first line does not match.");
        assertEquals("    public static void main(String[] args) {", lines.get(1), "The second line does not match.");
        assertEquals("        System.out.println(\"Hello, World!\");", lines.get(2), "The third line does not match.");
        assertEquals("    }", lines.get(3), "The fourth line does not match.");
        assertEquals("}", lines.get(4), "The fifth line does not match.");
    }

    @Test
    public void testReadLinesWithNonExistentJavaFile(@TempDir Path tempDir) throws IOException {
        List<String> lines = fileManager.readLines("NonExistentFile.java");

        assertTrue(lines.isEmpty(), "The list should be empty for a Java file that does not exist.");
    }

    @Test
    public void testGetJavaFilePaths(@TempDir Path tempDir) throws IOException {
        FileManager fileManager = new FileManager(tempDir.toString());
    
        File tempFile1 = tempDir.resolve("File1.java").toFile();
        File tempFile2 = tempDir.resolve("File2.java").toFile();
    
        tempFile1.createNewFile();
        tempFile2.createNewFile();
    
        List<String> filePaths = fileManager.getAllFilePaths();
    
        assertEquals(2, filePaths.size(), "There should be 2 Java files in the directory.");
        assertTrue(filePaths.contains(tempFile1.getAbsolutePath()), "The File1.java file should be listed.");
        assertTrue(filePaths.contains(tempFile2.getAbsolutePath()), "The File2.java file should be listed.");
    }
    

    @Test
    public void testGetAllFilePathsWithInvalidDirectory() {
        fileManager = new FileManager("/invalid/directory");

        List<String> fileNames = fileManager.getAllFilePaths();

        assertTrue(fileNames.isEmpty(), "The list should be empty for an invalid directory.");
    }

    @Test
    public void testGetDirectoryName(@TempDir Path tempDir) {
        FileManager fileManager = new FileManager(tempDir.toString());
        
        assertEquals(tempDir.getFileName().toString(), fileManager.getDirectoryName(), "The directory name must be correct.");
    }
}
