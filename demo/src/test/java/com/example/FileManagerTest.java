package com.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileManagerTest {

    public TemporaryFolder tempFolder = new TemporaryFolder();

    private FileManager fileManager;

    @Before
    public void setUp() throws IOException {
        tempFolder.create();
        fileManager = new FileManager(tempFolder.getRoot().getAbsolutePath());
    }

    @Test
    public void testReadLinesWithValidJavaFile() throws IOException {
        File tempFile = tempFolder.newFile("TestFile.java");
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

        List<String> lines = fileManager.readLines("TestFile.java");

        assertEquals("The Java file should have 5 lines.", 5, lines.size());
        assertEquals(
            "The first line does not match.", 
            "public class TestFile {", 
            lines.get(0)
        );
        assertEquals(
            "The second line does not match.", 
            "    public static void main(String[] args) {", 
            lines.get(1)
        );
        assertEquals(
            "The third line does not match.", 
            "        System.out.println(\"Hello, World!\");", 
            lines.get(2)
        );
        assertEquals(
            "The fourth line does not match.", 
            "    }", 
            lines.get(3)
        );
        assertEquals(
            "The fifth line does not match.", 
            "}", 
            lines.get(4)
        );
    }

    @Test
    public void testReadLinesWithNonExistentJavaFile() throws IOException {
        List<String> lines = fileManager.readLines("NonExistentFile.java");

        assertTrue(
            "The list should be empty for a Java file that does not exist.", 
            lines.isEmpty()
        );
    }

    @Test
    public void testGetJavaFilePaths() throws IOException {
        File tempFile1 = tempFolder.newFile("File1.java");
        File tempFile2 = tempFolder.newFile("File2.java");

        List<String> filePaths = fileManager.getAllFilePaths();

        assertEquals(
            "There should be 2 Java files in the directory.", 
            2, 
            filePaths.size()
        );
        assertTrue(
            "The File1.java file should be listed.", 
            filePaths.contains(tempFile1.getAbsolutePath()) 
        );
        assertTrue(
            "The File2.java file should be listed.", 
            filePaths.contains(tempFile2.getAbsolutePath())
        );
    }

    @Test
    public void testGetAllFilePathsWithInvalidDirectory() {
        fileManager = new FileManager("/invalid/directory");

        List<String> fileNames = fileManager.getAllFilePaths();

        assertTrue(
            "The list should be empty for an invalid directory.", 
            fileNames.isEmpty()
        );
    }

    @Test
    public void testgetDirectoryName() {
        assertEquals(
            "The directory name must be correct.", 
            tempFolder.getRoot().getName(), 
            fileManager.getDirectoryName()
        );
    }
}