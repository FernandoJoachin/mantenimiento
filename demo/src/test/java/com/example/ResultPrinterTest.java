package com.example;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test suite for the ResultPrinter class, responsible for verifying the
 * correct formatting and output of the line count table.
 * <p>
 * This class tests the table generation functionality with different inputs,
 * including normal, long, and short values for program name and line counts.
 * The tests ensure that the program displays the output correctly formatted.
 * </p>
 */
public class ResultPrinterTest {

    /**
     * Tests the normal case where the program name and line counts are typical
     * values for a program.
     * <p>
     * The test compares the actual table output with the expected formatted
     * string for normal values. This verifies the correct behavior of the
     * table formatting.
     * </p>
     */
    @Test
    public void testPrintResults_NormalCase() {
        String programName = "Programa1";
        int physicalLOC = 120;
        int logicalLOC = 100;

        String expectedOutput = 
                "+-----------+-------------+-------------+\n" +
                "| Programa  | LOC Lógicas | LOC Físicas |\n" +
                "+-----------+-------------+-------------+\n" +
                "| Programa1 | 100         | 120         |\n" +
                "+-----------+-------------+-------------+\n";

        String actualOutput = invokeBuildTable(programName, physicalLOC, logicalLOC);
        assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Tests the case with a long program name and large numbers for the line counts.
     * <p>
     * This test ensures that the formatting remains intact when the program name
     * is very long and the line counts are set to the maximum integer value.
     * </p>
     */
    @Test
    public void testPrintResults_LongProgramNameAndLargeNumbers() {
        String programName = "NombreDeProgramaExtraSuperLargo";
        int physicalLOC = Integer.MAX_VALUE;
        int logicalLOC = Integer.MAX_VALUE;

        String expectedOutput = 
                "+---------------------------------+-------------+-------------+\n" +
                "| Programa                        | LOC Lógicas | LOC Físicas |\n" +
                "+---------------------------------+-------------+-------------+\n" +
                "| NombreDeProgramaExtraSuperLargo | " + physicalLOC + "  | " + logicalLOC + "  |\n" +
                "+---------------------------------+-------------+-------------+\n";
    
        String actualOutput = invokeBuildTable(programName, physicalLOC, logicalLOC);
        assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Tests the case with a very short program name and minimal values for the
     * line counts.
     * <p>
     * This test ensures that the table formatting works correctly even when the
     * program name and line counts are extremely short.
     * </p>
     */
    @Test
    public void testPrintResults_ShortProgramNameAndSmallNumbers() {
        String programName = "A";
        int physicalLOC = 1;
        int logicalLOC = 1;

        String expectedOutput = 
                "+----------+-------------+-------------+\n" +
                "| Programa | LOC Lógicas | LOC Físicas |\n" +
                "+----------+-------------+-------------+\n" +
                "| A        | 1           | 1           |\n" +
                "+----------+-------------+-------------+\n";

        String actualOutput = invokeBuildTable(programName, physicalLOC, logicalLOC);
        assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Helper method to invoke the private method 'buildTable' from the
     * ResultPrinter class using reflection.
     * <p>
     * The 'buildTable' method is private, and in order to test it, this helper
     * method uses reflection to access and invoke the method. This approach allows
     * us to test the functionality of the table formatting without modifying the
     * access level of the original method.
     * </p>
     * 
     * @param programName  the name of the program
     * @param physicalLOC  the number of physical lines of code
     * @param logicalLOC   the number of logical lines of code
     * @return the generated table string
     */
    private String invokeBuildTable(String programName, int physicalLOC, int logicalLOC) {
        try {
            var method = ResultPrinter.class.getDeclaredMethod("buildTable", String.class, int.class, int.class);
            method.setAccessible(true);  // Allow access to the private method
            return (String) method.invoke(null, programName, physicalLOC, logicalLOC);
        } catch (Exception e) {
            throw new RuntimeException("Error invoking buildTable method", e);
        }
    }
}
