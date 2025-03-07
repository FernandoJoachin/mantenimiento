package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.exceptions.FileException;

/**
 * Unit tests for the PhysicalLineCounter class.
 * This class tests the functionality of counting physical lines of code
 * in a Java file while ignoring comments and empty lines.
 */
public class PhysicalLineCounterTest {

    PhysicalLineCounter counter = new PhysicalLineCounter();
    
    /**
     * Creates a temporary Java file with the given lines of content.
     * 
     * @param lines List of strings representing lines in the file
     * @return A JavaFile object representing the created temporary file
     * @throws IOException if an I/O error occurs while creating the file
     * @throws FileException if there is an issue handling the file
     */
    private JavaFile createTempJavaFile(List<String> lines) throws IOException, FileException {
        Path tempFile = Files.createTempFile("testFile", ".java");
        Files.write(tempFile, lines);
        return new JavaFile(tempFile.toString(), tempFile.getFileName().toString());
    }

    /**
     * Tests counting lines in a file that contains only code lines.
     * Expects the counter to return the exact number of lines.
     * 
     * @throws FileException if an issue occurs while processing the file
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testCountWithOnlyCode() throws FileException, IOException {
        JavaFile javaFile = createTempJavaFile(Arrays.asList(
            "int a = 0;",
            "a++;",
            "System.out.println(a);"
        ));

        int expected = 3;
        int actual = counter.count(javaFile);
        assertEquals(
            expected, 
            actual, 
            "Expected count for only code lines should be 3"
        );
    }

    /**
     * Tests counting lines in a file that contains only comments and empty lines.
     * Expects the counter to return zero.
     * 
     * @throws FileException if an issue occurs while processing the file
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testCountWithOnlyComments() throws FileException, IOException {
        JavaFile javaFile = createTempJavaFile(Arrays.asList(
            "// Line comment",
            " ",
            "/* Block comment */",
            ""
        ));

        int expected = 0;
        int actual = counter.count(javaFile);
        assertEquals(
            expected,
            actual,
            "Expected count for file with only comments and empty lines should be 0"
        );
    }

    /**
     * Tests counting lines in a file that contains a mix of code, comments, and empty lines.
     * Expects the counter to return the number of effective code lines.
     * 
     * @throws FileException if an issue occurs while processing the file
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testCountWithMixedInput() throws FileException, IOException {
        JavaFile javaFile = createTempJavaFile(Arrays.asList(
            "/* Start block comment",
            "Block comment",
            "End block comment*/",
            "int a = 5; // assignment",
            " ",
            "System.out.println(a);"
        ));

        int expected = 2;
        int actual = counter.count(javaFile);
        assertEquals(
            expected,
            actual,
            "Expected count for mixed input should be 2 (only counting effective code lines)"
        );
    }
}
