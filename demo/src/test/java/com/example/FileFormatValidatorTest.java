package com.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.exceptions.FileFormatException;

public class FileFormatValidatorTest {
    @Test
    public void testIsValidFileType_Correct() {
        List<String> dummyLines = Arrays.asList(
            "public class Test {",
            "    public static void main(String[] args) {",
            "        System.out.println(\"Hello, World!\");",
            "    }",
            "}"
        );

        assertDoesNotThrow(() -> 
            FileFormatValidator.isValidFileFormat("ValidFile.java", dummyLines),
            "The file with .java extension must be valid."
        );
    }

    @Test
    public void testIsValidFileType_Incorrect() {
        List<String> dummyLines = Arrays.asList(
            "public class Test {",
            "    public static void main(String[] args) {",
            "        System.out.println(\"Hello, World!\");",
            "    }",
            "}"
        );

        assertDoesNotThrow(() -> 
            FileFormatValidator.isValidFileFormat("ValidFile.java", dummyLines),
            "The file with .java extension must be valid."
        );
    }

    @Test
    public void testIsValidLineLength_Correct() {
        List<String> shortLineFile = Arrays.asList(
            "public class Test {",
            "    public static void main(String[] args) {",
            "        System.out.println(\"Hello, World!\");",
            "    }",
            "}"
        );

        assertDoesNotThrow(() ->
            FileFormatValidator.isValidFileFormat("ValidFile.java", shortLineFile),
            "The file with lines with less than 120 characters must be valid."
        );
    }

    @Test
    public void testIsValidLineLength_Incorrect() {
        List<String> longLineFile = Arrays.asList(
            "public class Test {",
            "    public static void main(String[] args) {",
            "  String mensaje = \"El sistema ha procesado la solicitud exitosamente y ha generado un identificador único para su referencia: 1234567890. Por favor, guarde este identificador para futuras consultas.\";",
            "        System.out.println(mensaje);",
            "    }",
            "}"
        );

        assertThrows(FileFormatException.class, () ->
            FileFormatValidator.isValidFileFormat("InvalidFile.java", longLineFile),
            "The file with lines with more than 120 characters must be invalid."
        );

    }

    @Test
    public void testIsValidMultipleStatements_Correct() {
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

        assertDoesNotThrow(() ->
            FileFormatValidator.isValidFileFormat("ValidFile.java", singleStatementFile),
            "The file with lines with only one executable statement must be valid."
        );
    }

    @Test
    public void testIsValidMultipleStatements_Incorrect() {
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

        assertThrows(FileFormatException.class, () ->
            FileFormatValidator.isValidFileFormat("InvalidFile.java", multipleStatementsFile),
            "File with lines with multiple executable statements must be invalid."
        );

    }
    
    @Test
    public void testIsValidBracesStyle_Correct() {
        List<String> validBraceFile = Arrays.asList(
            "public class Test {",
            "    public static void main(String[] args) {",
            "        System.out.println(\"Hello, World!\");",
            "    }",
            "}"
        );

        assertDoesNotThrow(() ->
            FileFormatValidator.isValidFileFormat("ValidFile.java", validBraceFile),
            "File for lines with K&R style braces must be valid."
        );
    }

    @Test
    public void testIsValidBracesStyle_Incorrect() {
        List<String> invalidBraceFile = Arrays.asList(
            "public class Test {",
            "    public static void main(String[] args)",
            "{",
            "        System.out.println(\"Hello, World!\");",
            "    }",
            "}"
        );

        assertThrows(FileFormatException.class, () ->
            FileFormatValidator.isValidFileFormat("InvalidFile.java", invalidBraceFile),
            "File for lines with Allman style braces must be invalid."
        );
    }

    @Test
    public void testIsValidImportStatement_Correct() {
        List<String> explicitImportFile = Arrays.asList(
            "import java.util.List;",
            "public class Test {",
            "    public static void main(String[] args) {",
            "        System.out.println(\"Hello, World!\");",
            "    }",
            "}"
        );

        assertDoesNotThrow(() ->
            FileFormatValidator.isValidFileFormat("ValidFile.java", explicitImportFile),
            "File with explicit import must be valid."
        );
    }

    @Test
    public void testIsValidImportStatement_Incorrect() {
        List<String> wildcardImportFile = Arrays.asList(
            "import java.util.*;",
            "public class Test {",
            "    public static void main(String[] args) {",
            "        System.out.println(\"Hello, World!\");",
            "    }",
            "}"
        );

        assertThrows(FileFormatException.class, () ->
            FileFormatValidator.isValidFileFormat("InvalidFile.java", wildcardImportFile),
            "File with wildcard import must be invalid."
        );
    }

    @Test
    public void testIsValidAnnotationFormat_Correct() {
        List<String> correctAnnotationFile = Arrays.asList(
            "@Override",
            "public void method() {",
            "    System.out.println(\"Hello, World!\");",
            "}"
        );

        assertDoesNotThrow(() ->
            FileFormatValidator.isValidFileFormat("ValidFile.java", correctAnnotationFile),
            "File with annotation on a separate line must be valid."
        );
    }

    @Test
    public void testIsValidAnnotationFormat_Incorrect() {
        List<String> annotationWithDeclarationFile = Arrays.asList(
            "@Override public void method() {",
            "    System.out.println(\"Hello, World!\");",
            "}"
        );

        assertThrows(FileFormatException.class, () ->
            FileFormatValidator.isValidFileFormat("InvalidFile.java", annotationWithDeclarationFile),
            "File with annotation on the same line as a declaration must be invalid."
        );
    }
}
