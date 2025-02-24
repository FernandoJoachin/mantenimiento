package com.example;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class FileFormatValidatorTest {
    @Test
    public void testIsValidFileType() {
        List<String> dummyLines = Arrays.asList(
            "public class Test {",
            "    public static void main(String[] args) {",
            "        System.out.println(\"Hello, World!\");",
            "    }",
            "}"
        );
        assertTrue("The file with .java extension must be valid.",
            FileFormatValidator.isValidFileFormat("ValidFile.java", dummyLines));

        assertFalse("The file with extension .txt must be invalid.",
            FileFormatValidator.isValidFileFormat("InvalidFile.txt", dummyLines));
    }

    @Test
    public void testIsValidLineLength() {
        List<String> shortLineFile = Arrays.asList(
            "public class Test {",
            "    public static void main(String[] args) {",
            "        System.out.println(\"Hello, World!\");",
            "    }",
            "}"
        );

        List<String> longLineFile = Arrays.asList(
            "public class Test {",
            "    public static void main(String[] args) {",
            "  String mensaje = \"El sistema ha procesado la solicitud exitosamente y ha generado un identificador único para su referencia: 1234567890. Por favor, guarde este identificador para futuras consultas.\";",
            "        System.out.println(mensaje);",
            "    }",
            "}"
        );

        assertTrue("The file with lines with less than 120 characters must be valid.",
            FileFormatValidator.isValidFileFormat("ValidFile.java", shortLineFile));

        assertFalse("The file with lines with more than 120 characters must be invalid.",
            FileFormatValidator.isValidFileFormat("InvalidFile.java", longLineFile));
    }

    @Test
    public void testIsValidMultipleStatements() {
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


        assertTrue("The file with lines with only one executable statement must be valid.",
            FileFormatValidator.isValidFileFormat("ValidFile.java", singleStatementFile));

        assertFalse("File with lines with multiple executable statements must be invalid.",
            FileFormatValidator.isValidFileFormat("InvalidFile.java", multipleStatementsFile));
    }

    @Test
    public void testIsValidBracesStyle() {
        List<String> validBraceFile = Arrays.asList(
            "public class Test {",
            "    public static void main(String[] args) {",
            "        System.out.println(\"Hello, World!\");",
            "    }",
            "}"
        );
        
        List<String> invalidBraceFile = Arrays.asList(
            "public class Test {",
            "    public static void main(String[] args)",
            "{",
            "        System.out.println(\"Hello, World!\");",
            "    }",
            "}"
        );

        assertTrue("File for lines with K&R style braces must be valid.",
            FileFormatValidator.isValidFileFormat("ValidFile.java", validBraceFile));

        assertFalse("File for lines with Allman style braces must be invalid.",
            FileFormatValidator.isValidFileFormat("InvalidFile.java", invalidBraceFile));
    }
}
