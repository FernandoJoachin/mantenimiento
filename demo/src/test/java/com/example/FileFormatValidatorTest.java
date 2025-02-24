package com.example;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

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

        assertTrue(
            "The file with .java extension must be valid.",
            FileFormatValidator.isValidFileFormat("ValidFile.java", dummyLines)
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

        assertFalse(
            "The file with extension .txt must be invalid.",
            FileFormatValidator.isValidFileFormat("InvalidFile.txt", dummyLines)
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

        assertTrue(
            "The file with lines with less than 120 characters must be valid.",
            FileFormatValidator.isValidFileFormat("ValidFile.java", shortLineFile)
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

        assertFalse(
            "The file with lines with more than 120 characters must be invalid.",
            FileFormatValidator.isValidFileFormat("InvalidFile.java", longLineFile)
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

        assertTrue(
            "The file with lines with only one executable statement must be valid.",
            FileFormatValidator.isValidFileFormat("ValidFile.java", singleStatementFile)
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

        assertFalse(
            "File with lines with multiple executable statements must be invalid.",
            FileFormatValidator.isValidFileFormat("InvalidFile.java", multipleStatementsFile)
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

        assertTrue(
            "File for lines with K&R style braces must be valid.",
            FileFormatValidator.isValidFileFormat("ValidFile.java", validBraceFile)
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

        assertFalse(
            "File for lines with Allman style braces must be invalid.",
            FileFormatValidator.isValidFileFormat("InvalidFile.java", invalidBraceFile)
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

        assertTrue(
            "File with explicit import must be valid.",
            FileFormatValidator.isValidFileFormat("ValidFile.java", explicitImportFile)
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

        assertFalse(
            "File with wildcard import must be invalid.",
            FileFormatValidator.isValidFileFormat("InvalidFile.java", wildcardImportFile)
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

        assertTrue(
            "File with annotation on a separate line must be valid.",
            FileFormatValidator.isValidFileFormat("ValidFile.java", correctAnnotationFile)
        );
    }

    @Test
    public void testIsValidAnnotationFormat_Incorrect() {
        List<String> annotationWithDeclarationFile = Arrays.asList(
            "@Override public void method() {",
            "    System.out.println(\"Hello, World!\");",
            "}"
        );

        assertFalse(
            "File with annotation on the same line as a declaration must be invalid.",
            FileFormatValidator.isValidFileFormat("InvalidFile.java", annotationWithDeclarationFile)
        );
    }
}
