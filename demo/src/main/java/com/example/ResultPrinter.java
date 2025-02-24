package com.example;
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
        String titleProgram = "Programa";
        String titleLogicalLOC = "LOC Lógicas";
        String titlePhysicalLOC = "LOC Físicas";

        int maxProgramLength = getMaxColumnWidth(titleProgram, programName);
        int maxLogicalLength = getMaxColumnWidth(titleLogicalLOC, String.valueOf(logicalLOC));
        int maxPhysicalLength = getMaxColumnWidth(titlePhysicalLOC, String.valueOf(physicalLOC));

        String headerFormat = createHeader(maxProgramLength, maxLogicalLength, maxPhysicalLength);
        String separator = createSeparator(maxProgramLength, maxLogicalLength, maxPhysicalLength);

        StringBuilder table = new StringBuilder();
        table.append(separator);
        table.append(String.format(headerFormat, titleProgram, titleLogicalLOC, titlePhysicalLOC));
        table.append(separator);
        table.append(String.format(headerFormat, programName, logicalLOC, physicalLOC));
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
     * @param maxLogicalLength   The maximum width of the column for the Logical LOC.
     * @param maxPhysicalLength  The maximum width of the column for the Physical LOC.
     * @return A string representing the formatted header row of the table.
     */
    private static String createHeader(int maxProgramLength, int maxLogicalLength, int maxPhysicalLength) {
        return "| %-" + maxProgramLength + "s | %-" + maxLogicalLength + "s | %-" + maxPhysicalLength + "s |\n";
    }

    /**
     * Creates the separator line for the table, adjusting the length of each
     * section based on the column widths.
     *
     * @param maxProgramLength   The maximum width of the column for the program name.
     * @param maxLogicalLength   The maximum width of the column for the Logical LOC.
     * @param maxPhysicalLength  The maximum width of the column for the Physical LOC.
     * @return A string representing the separator line of the table.
     */
    private static String createSeparator(int maxProgramLength, int maxLogicalLength, int maxPhysicalLength) {
        return "+" + "-".repeat(maxProgramLength + 2) + "+"
            + "-".repeat(maxLogicalLength + 2) + "+"
            + "-".repeat(maxPhysicalLength + 2) + "+\n";
    }

}
