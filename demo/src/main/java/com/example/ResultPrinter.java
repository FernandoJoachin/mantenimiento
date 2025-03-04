package com.example;

import com.example.constants.SymbolsConstants;

/**
 * The ResultPrinter class is responsible for formatting and printing a table 
 * with the results of a program's line count.
 * 
 * <p>It prints a table in the console displaying the program name, the number 
 * of logical lines, and the number of physical lines of code.</p>
 * 
 */
public class ResultPrinter {

    /**
     * Represents the title for the program column.
     */
    public static final String TITLE_PROGRAM = "Programa";

    /**
     * Represents the title for the physical lines of code (LOC) column.
     */
    public static final String TITLE_PHYSICAL_LOC = "LOC Físicas";

    /**
     * Represents the title for the logical lines of code (LOC) column.
     */
    public static final String TITLE_LOGICAL_LOC = "LOC Lógicas";

    /**
     * Defines the format template for a table column.
     * This format ensures that each column has a fixed width, aligning text properly.
     * The placeholders `%d` are used to specify column width dynamically.
     */
    public static final String COLUMN_FORMAT_TEMPLATE = "| %%-%ds | %%-%ds | %%-%ds |\n";

    /**
     * Defines the horizontal padding used in table formatting.
     * This value represents the number of spaces added on each side
     * of the content within a cell to improve readability.
     */
    public static final int HORIZONTAL_PADDING = 2;
    
    /**
     * Prints a table in the console with the results of the line count.
     * 
     * @param programName Name of the analyzed program.
     * @param physicalLOC Number of physical lines of code (Physical LOC).
     * @param logicalLOC Number of logical lines of code (Logical LOC).
     */
    public static void printResults(String programName, int physicalLOC, int logicalLOC) {
        String tableText = buildTable(programName, physicalLOC, logicalLOC);
        System.out.println(tableText);
    }   

    /**
     * Builds a formatted table with the results of the line count.
     * 
     * @param programName Name of the analyzed program.
     * @param physicalLOC Number of physical lines of code (Physical LOC).
     * @param logicalLOC Number of logical lines of code (Logical LOC).
     * @return A formatted string representing the table with the data.
     */
    private static String buildTable(String programName, int physicalLOC, int logicalLOC) {

        int maxProgramLength = getMaxColumnWidth(TITLE_PROGRAM, programName);
        int maxPhysicalLength = getMaxColumnWidth(TITLE_PHYSICAL_LOC, String.valueOf(physicalLOC));
        int maxLogicalLength = getMaxColumnWidth(TITLE_LOGICAL_LOC, String.valueOf(logicalLOC));

        String headerFormat = createHeaderFormat(maxProgramLength, maxPhysicalLength, maxLogicalLength);
        String separator = createSeparator(maxProgramLength, maxPhysicalLength, maxLogicalLength);

        StringBuilder table = new StringBuilder();
        table.append(separator);
        table.append(String.format(headerFormat, TITLE_PROGRAM, TITLE_PHYSICAL_LOC, TITLE_LOGICAL_LOC));
        table.append(separator);
        table.append(String.format(headerFormat, programName, physicalLOC, logicalLOC));
        table.append(separator);

        return table.toString();
    }

    /**
     * Calculates the maximum column width based on the length of the title and the corresponding value.
     *
     * @param title The title of the column.
     * @param value The value to be displayed in the column.
     * @return The maximum width required for the column.
     */
    private static int getMaxColumnWidth(String title, String value) {
        return Math.max(title.length(), value.length());
    }

    /**
     * Creates the header row of the table, formatting the column titles
     * with the appropriate column widths.
     *
     * @param maxProgramLength   The maximum width of the column for the program name.
     * @param maxPhysicalLength  The maximum width of the column for the Physical LOC.
     * @param maxLogicalLength   The maximum width of the column for the Logical LOC.
     * @return A string representing the formatted header row of the table.
     */
    private static String createHeaderFormat(int maxProgramLength, int maxPhysicalLength, int maxLogicalLength) {
        String header = String.format(
            COLUMN_FORMAT_TEMPLATE,
            maxProgramLength,
            maxPhysicalLength,
            maxLogicalLength
        );
        return header;
    }

    /**
     * Creates the separator line for the table, adjusting the length of each
     * section based on the column widths.
     *
     * @param maxProgramLength   The maximum width of the column for the program name.
     * @param maxPhysicalLength  The maximum width of the column for the Physical LOC.
     * @param maxLogicalLength   The maximum width of the column for the Logical LOC.
     * @return A string representing the separator line of the table.
     */
    private static String createSeparator(int maxProgramLength, int maxPhysicalLength, int maxLogicalLength) {
        return SymbolsConstants.PLUS_SIGN + 
        SymbolsConstants.MINUS_SIGN.repeat(maxProgramLength + HORIZONTAL_PADDING) + 
        SymbolsConstants.PLUS_SIGN + 
        SymbolsConstants.MINUS_SIGN.repeat(maxPhysicalLength + HORIZONTAL_PADDING) +
        SymbolsConstants.PLUS_SIGN + 
        SymbolsConstants.MINUS_SIGN.repeat(maxLogicalLength + HORIZONTAL_PADDING) + 
        SymbolsConstants.PLUS_SIGN +
        "\n";
    }

}
