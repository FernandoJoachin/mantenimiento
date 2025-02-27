package com.example;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class AppTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testAppIntegration(@TempDir Path tempDir) throws IOException {
        File testFile1 = new File(tempDir.toFile(), "Example.java");
        String javaCode1 = 
            "package com.example.files;\n" +
            "\n" +
            "public class Example {\n" +
            "    // Single-line comment\n" +
            "\n" +
            "    /* \n" +
            "     * Multi-line comment\n" +
            "     * This should not be counted as a logical line\n" +
            "     */\n" +
            "\n" +
            "    public static void main(String[] args) {\n" +
            "        System.out.println(\"Hello, world!\");\n" +
            "    }\n" +
            "\n" +
            "    public void methodOne() {\n" +
            "        int x = 10;\n" +
            "        int y = 20;\n" +
            "        System.out.println(x + y);\n" +
            "    }\n" +
            "\n" +
            "    public int methodTwo(int a, int b) {\n" +
            "        return a + b;\n" +
            "    }\n" +
            "}\n";
        Files.write(testFile1.toPath(), javaCode1.getBytes());

        File testFile2 = new File(tempDir.toFile(), "Example2.java");
        String javaCode2 = 
            "package com.example.files;\n" +
            "\n" +
            "public class Example2 {\n" +
            "    public void methodOne() {\n" +
            "        int x = 10;\n" +
            "        int y = 20;\n" +
            "        System.out.println(x + y);\n" +
            "    }\n" +
            "\n" +
            "    public int methodTwo(int a, int b) {\n" +
            "        return a + b;\n" +
            "    }\n" +
            "}\n";
        Files.write(testFile2.toPath(), javaCode2.getBytes());

        String[] args = { tempDir.toString() };
        App.main(args);

        String output = outContent.toString();
        assertTrue(output.contains("| " + tempDir.getFileName() + " | 9           | 25          |"));
    }
    
}