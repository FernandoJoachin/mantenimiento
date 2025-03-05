package com.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
import com.example.exceptions.FileFormatException;
import com.example.validators.FileFormatValidator;

public class FileFormatValidatorTest {
    @TempDir
    Path tempDir;

    @Test
    public void testIsValidLineLength_Correct() throws IOException, FileException {
        File javaFile = new File(tempDir.toFile(), "ValidFile.java");
        List<String> shortLineFile = Arrays.asList(
            "public class Test {",
            "    public static void main(String[] args) {",
            "        System.out.println(\"Hello, World!\");",
            "    }",
            "}"
        );

        Files.write(javaFile.toPath(), shortLineFile);
        JavaFile javaFileObject = new JavaFile(javaFile.getAbsolutePath(), javaFile.getName());

        assertDoesNotThrow(() ->
            FileFormatValidator.isValidFileFormat(javaFileObject),
            "The file with lines with less than 120 characters must be valid."
        );
    }

    @Test
    public void testIsValidLineLength_Incorrect() throws IOException, FileException, FileFormatException {
        File javaFile = new File(tempDir.toFile(), "InvalidFile.java");
        List<String> longLineFile = Arrays.asList(
            "public class Test {",
            "    public static void main(String[] args) {",
            "  String mensaje = \"El sistema ha procesado la solicitud exitosamente y ha generado un identificador único para su referencia: 1234567890. Por favor, guarde este identificador para futuras consultas.\";",
            "        System.out.println(mensaje);",
            "    }",
            "}"
        );

        Files.write(javaFile.toPath(), longLineFile);
        JavaFile javaFileObject = new JavaFile(javaFile.getAbsolutePath(), javaFile.getName());

        assertThrows(FileFormatException.class, () ->
            FileFormatValidator.isValidFileFormat(javaFileObject),
            "The file with lines with more than 120 characters must be invalid."
        );
    }

    @Test
    public void testIsValidMultipleStatements_Correct() throws IOException, FileException, FileFormatException {
        File javaFile = new File(tempDir.toFile(), "ValidFile.java");
        List<String> singleStatementFile = Arrays.asList(
            "public class Test {",
            "    public static void main(String[] args) {",
            "       int a = 0;",
            "       for (int i = 0; i < 10; i++) {",
            "           System.out.println(a+i);",
            "       }",
            "    }",
            "}"
        );

        Files.write(javaFile.toPath(), singleStatementFile);
        JavaFile javaFileObject = new JavaFile(javaFile.getAbsolutePath(), javaFile.getName());

        assertDoesNotThrow(() ->
            FileFormatValidator.isValidFileFormat(javaFileObject),
            "The file with lines with only one executable statement must be valid."
        );
    }

    @Test
    public void testIsValidMultipleStatements_Incorrect() throws IOException, FileException, FileFormatException {
        File javaFile = new File(tempDir.toFile(), "InvalidFile.java");
        List<String> multipleStatementsFile = Arrays.asList(
            "public class Test {",
            "    public static void main(String[] args) {",
            "       int a = 5; int b = 10;",
            "       for (int i = 0; i < 10; i++) {",
            "           System.out.println(a+b+i);",
            "       }",
            "    }",
            "}"
        );

        Files.write(javaFile.toPath(), multipleStatementsFile);
        JavaFile javaFileObject = new JavaFile(javaFile.getAbsolutePath(), javaFile.getName());

        assertThrows(FileFormatException.class, () ->
            FileFormatValidator.isValidFileFormat(javaFileObject),
            "File with lines with multiple executable statements must be invalid."
        );
    }

    @Test
    public void testIsValidBracesStyle_Correct() throws IOException, FileException, FileFormatException {
        File javaFile = new File(tempDir.toFile(), "ValidFile.java");
        List<String> validBraceFile = Arrays.asList(
            "public class Test {",
            "    public static void main(String[] args) {",
            "        System.out.println(\"Hello, World!\");",
            "    }",
            "}"
        );

        Files.write(javaFile.toPath(), validBraceFile);
        JavaFile javaFileObject = new JavaFile(javaFile.getAbsolutePath(), javaFile.getName());

        assertDoesNotThrow(() ->
            FileFormatValidator.isValidFileFormat(javaFileObject),
            "File for lines with K&R style braces must be valid."
        );
    }

    @Test
    public void testIsValidBracesStyle_Incorrect() throws IOException, FileException, FileFormatException {
        File javaFile = new File(tempDir.toFile(), "InvalidFile.java");
        List<String> invalidBraceFile = Arrays.asList(
            "public class Test {",
            "    public static void main(String[] args)",
            "{",
            "        System.out.println(\"Hello, World!\");",
            "    }",
            "}"
        );

        Files.write(javaFile.toPath(), invalidBraceFile);
        JavaFile javaFileObject = new JavaFile(javaFile.getAbsolutePath(), javaFile.getName());

        assertThrows(FileFormatException.class, () ->
            FileFormatValidator.isValidFileFormat(javaFileObject),
            "File for lines with Allman style braces must be invalid."
        );
    }

    @Test
    public void testIsValidImportStatement_Correct() throws IOException, FileException, FileFormatException {
        File javaFile = new File(tempDir.toFile(), "ValidFile.java");
        List<String> explicitImportFile = Arrays.asList(
            "import java.util.List;",
            "public class Test {",
            "    public static void main(String[] args) {",
            "        System.out.println(\"Hello, World!\");",
            "    }",
            "}"
        );

        Files.write(javaFile.toPath(), explicitImportFile);
        JavaFile javaFileObject = new JavaFile(javaFile.getAbsolutePath(), javaFile.getName());

        assertDoesNotThrow(() ->
            FileFormatValidator.isValidFileFormat(javaFileObject),
            "File with explicit import must be valid."
        );
    }

    @Test
    public void testIsValidImportStatement_Incorrect() throws IOException, FileException, FileFormatException {
        File javaFile = new File(tempDir.toFile(), "InvalidFile.java");
        List<String> wildcardImportFile = Arrays.asList(
            "import java.util.*;",
            "public class Test {",
            "    public static void main(String[] args) {",
            "        System.out.println(\"Hello, World!\");",
            "    }",
            "}"
        );

        Files.write(javaFile.toPath(), wildcardImportFile);
        JavaFile javaFileObject = new JavaFile(javaFile.getAbsolutePath(), javaFile.getName());

        assertThrows(FileFormatException.class, () ->
            FileFormatValidator.isValidFileFormat(javaFileObject),
            "File with wildcard import must be invalid."
        );
    }

    @Test
    public void testIsValidAnnotationFormat_Correct() throws IOException, FileException, FileFormatException {
        File javaFile = new File(tempDir.toFile(), "ValidFile.java");
        List<String> correctAnnotationFile = Arrays.asList(
            "@Override",
            "public void method() {",
            "    System.out.println(\"Hello, World!\");",
            "}"
        );

        Files.write(javaFile.toPath(), correctAnnotationFile);
        JavaFile javaFileObject = new JavaFile(javaFile.getAbsolutePath(), javaFile.getName());

        assertDoesNotThrow(() ->
            FileFormatValidator.isValidFileFormat(javaFileObject),
            "File with annotation on a separate line must be valid."
        );
    }

    @Test
    public void testIsValidAnnotationFormat_Incorrect() throws IOException, FileException, FileFormatException {
        File javaFile = new File(tempDir.toFile(), "InvalidFile.java");
        List<String> annotationWithDeclarationFile = Arrays.asList(
            "@Override public void method() {",
            "    System.out.println(\"Hello, World!\");",
            "}"
        );

        Files.write(javaFile.toPath(), annotationWithDeclarationFile);
        JavaFile javaFileObject = new JavaFile(javaFile.getAbsolutePath(), javaFile.getName());

        assertThrows(FileFormatException.class, () ->
            FileFormatValidator.isValidFileFormat(javaFileObject),
            "File with annotation on the same line as a declaration must be invalid."
        );
    }
}
