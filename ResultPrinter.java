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
     * Table header with column titles.
     */
    private static final String HEADER = "+------------+--------------+--------------+\n"
                                       + "| Programa   | LOC Lógicas  | LOC Físicas  |\n"
                                       + "+------------+--------------+--------------+\n";

    /**
     * Table footer marking the end of the format.
     */
    private static final String FOOTER = "+------------+--------------+--------------+\n";

    /**
     * Format for each row of the table, ensuring proper alignment of values.
     */
    private static final String ROW_FORMAT = "| %-10s | %-12d | %-12d |\n";

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
        StringBuilder resultText = new StringBuilder();
        resultText.append(HEADER);
        resultText.append(String.format(ROW_FORMAT, programName, logicalLOC, physicalLOC));
        resultText.append(FOOTER);
        return resultText.toString();
    }
}
