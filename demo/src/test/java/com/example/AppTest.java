package com.example;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

class AppTest {

    @Test
    public void testProcessDirectory_IntegrationTest() throws Exception {
        Path tempDir = Files.createTempDirectory("testDir");
        Path file1 = Files.createFile(tempDir.resolve("file1.txt"));
        Path file2 = Files.createFile(tempDir.resolve("file2.txt"));

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

        Files.write(file1, javaCode1.getBytes());

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

        Files.write(file2, javaCode2.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        App.processDirectory(tempDir.toString());

        System.setOut(originalOut);

        String output = outputStream.toString();
        System.out.println(output);
        assertTrue(output.contains("| " + tempDir.getFileName() + " | 25          | 9           |"));

        Files.delete(file1);
        Files.delete(file2);
        Files.delete(tempDir);
    }

}
