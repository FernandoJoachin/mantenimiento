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
 * Unit tests for the LogicalLineCounter class.
 * This class verifies the correct counting of logical lines in Java files.
 */
public class LogicalLineCounterTest {

    private LogicalLineCounter counter = new LogicalLineCounter();

    /**
     * Creates a temporary Java file with the given lines.
     *
     * @param lines List of lines to write to the temporary file.
     * @return A JavaFile object representing the created file.
     * @throws FileException If an error occurs in file processing.
     * @throws IOException If an I/O error occurs.
     */
    private JavaFile createTempJavaFile(List<String> lines) throws FileException, IOException {
        Path tempFile = Files.createTempFile("TestLogicalLineCounter", ".java");
        Files.write(tempFile, lines);
        return new JavaFile(tempFile.toString(), tempFile.getFileName().toString());
    }
    
    /**
     * Tests counting logical lines when no logical lines are present.
     */
    @Test
    public void testNoLogicalLines() throws FileException, IOException {
        List<String> lines = Arrays.asList(
            "package com.example;",
            "import java.util.List;",
            "",
            "// This is a comment",
            "@Deprecated",
            "int x = 0"
        );
        JavaFile javaFile = createTempJavaFile(lines);
        int logicalCount = counter.count(javaFile);
        assertEquals(0, logicalCount, "Expected 0 logical lines.");
    }
    
    /**
     * Tests counting logical lines for a class declaration.
     */
    @Test
    public void testClassDeclarationLogicalLine() throws FileException, IOException {
        List<String> lines = Arrays.asList(
            "package com.example;",
            "",
            "public class TestClass {",
            "    // class body",
            "}"
        );
        JavaFile javaFile = createTempJavaFile(lines);
        int logicalCount = counter.count(javaFile);
        assertEquals(1, logicalCount, "Expected 1 logical line for the class declaration.");
    }
    
    /**
     * Tests counting logical lines for an interface declaration.
     */
    @Test
    public void testInterfaceDeclarationLogicalLine() throws FileException, IOException {
        List<String> lines = Arrays.asList(
            "package com.example;",
            "",
            "public interface TestInterface {",
            "    // interface body",
            "}"
        );
        JavaFile javaFile = createTempJavaFile(lines);
        int logicalCount = counter.count(javaFile);
        assertEquals(1, logicalCount, "Expected 1 logical line for the interface declaration.");
    }
    
    /**
     * Tests counting logical lines for an enum declaration.
     */
    @Test
    public void testEnumDeclarationLogicalLine() throws FileException, IOException {
        List<String> lines = Arrays.asList(
            "package com.example;",
            "",
            "public enum TestEnum {",
            "    VALUE1, VALUE2;",
            "    // enum body",
            "}"
        );
        JavaFile javaFile = createTempJavaFile(lines);
        int logicalCount = counter.count(javaFile);
        assertEquals(1, logicalCount, "Expected 1 logical line for the enum declaration.");
    }
    
    /**
     * Tests counting logical lines for a method declaration.
     */
    @Test
    public void testMethodDeclarationLogicalLine() throws FileException, IOException {
        List<String> lines = Arrays.asList(
            "public static int dividir(int a, int b) throws ArithmeticException {",
            "    return a / b;",
            "}"
        );
        JavaFile javaFile = createTempJavaFile(lines);
        int logicalCount = counter.count(javaFile);
        assertEquals(1, logicalCount, "Expected 1 logical line for the method declaration.");
    }
    
    /**
     * Tests if an 'if' control structure is counted as one logical line.
     *
     * @throws FileException if there's an issue processing the file
     * @throws IOException   if an IO error occurs
     */
    @Test
    public void testIfLogicalLine() throws FileException, IOException {
        List<String> lines = Arrays.asList(
            "if (x > 0) {",
            "    // do something",
            "}"
        );
        JavaFile javaFile = createTempJavaFile(lines);
        int logicalCount = counter.count(javaFile);
        assertEquals(1, logicalCount, "Expected 1 logical line for the if control structure.");
    }

    /**
     * Tests if a 'for' loop is counted as one logical line.
     *
     * @throws FileException if there's an issue processing the file
     * @throws IOException   if an IO error occurs
     */
    @Test
    public void testForLogicalLine() throws FileException, IOException {
        List<String> lines = Arrays.asList(
            "for (int i = 0; i < 10; i++) {",
            "    // loop body",
            "}"
        );
        JavaFile javaFile = createTempJavaFile(lines);
        int logicalCount = counter.count(javaFile);
        assertEquals(1, logicalCount, "Expected 1 logical line for the for control structure.");
    }

    /**
     * Tests if a 'while' loop is counted as one logical line.
     *
     * @throws FileException if there's an issue processing the file
     * @throws IOException   if an IO error occurs
     */
    @Test
    public void testWhileLogicalLine() throws FileException, IOException {
        List<String> lines = Arrays.asList(
            "while (x < 10) {",
            "    // loop body",
            "}"
        );
        JavaFile javaFile = createTempJavaFile(lines);
        int logicalCount = counter.count(javaFile);
        assertEquals(1, logicalCount, "Expected 1 logical line for the while control structure.");
    }

    /**
     * Tests if a 'switch' statement is counted as one logical line.
     *
     * @throws FileException if there's an issue processing the file
     * @throws IOException   if an IO error occurs
     */
    @Test
    public void testSwitchLogicalLine() throws FileException, IOException {
        List<String> lines = Arrays.asList(
            "switch (x) {",
            "    case 1:",
            "        break;",
            "    default:",
            "        break;",
            "}"
        );
        JavaFile javaFile = createTempJavaFile(lines);
        int logicalCount = counter.count(javaFile);
        assertEquals(1, logicalCount, "Expected 1 logical line for the switch control structure.");
    }

    /**
     * Tests if a 'try-catch' block is counted as one logical line.
     *
     * @throws FileException if there's an issue processing the file
     * @throws IOException   if an IO error occurs
     */
    @Test
    public void testTryLogicalLine() throws FileException, IOException {
        List<String> lines = Arrays.asList(
            "try {",
            "    // try block",
            "} catch(Exception e) {",
            "    // handle exception",
            "}"
        );
        JavaFile javaFile = createTempJavaFile(lines);
        int logicalCount = counter.count(javaFile);
        assertEquals(1, logicalCount, "Expected 1 logical line for the try control structure.");
    }

    /**
     * Tests counting logical lines in a mixed Java class example.
     *
     * @throws FileException if there's an issue processing the file
     * @throws IOException   if an IO error occurs
     */
    @Test
    public void testMixedLogicalLines() throws FileException, IOException {
        List<String> lines = Arrays.asList(
            "package com.example;",
            "import java.util.List;",
            "",
            "public class MixedExample {",              
            "    Example example = new Example()",
            "    public MixedExample() {",               
            "        // constructor body",
            "    }",
            "",
            "    public void exampleMethod() {",          
            "        if (x > 0) {",                      
            "            System.out.println(\"Positive\");",
            "        }",
            "    }",
            "}"
        );
        JavaFile javaFile = createTempJavaFile(lines);
        int logicalCount = counter.count(javaFile);
        assertEquals(4, logicalCount, "Expected 4 logical lines in the mixed example.");
    }
}
