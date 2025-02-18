/**
 * La clase ResultPrinter se encarga de formatear e imprimir una tabla con los resultados
 * del conteo de líneas de código de un programa.
 * 
 * <p>Imprime en consola una tabla con el nombre del programa, la cantidad de líneas 
 * lógicas y físicas de código.</p>
 * 
 * @author JosePucMoo
 * @version 1.0
 */
public class ResultPrinter {

    /**
     * Cabecera de la tabla con los títulos de cada columna.
     */
    private static final String HEADER = "+------------+--------------+--------------+\n"
                                       + "| Programa   | LOC Lógicas  | LOC Físicas  |\n"
                                       + "+------------+--------------+--------------+\n";

    /**
     * Pie de la tabla que marca el final del formato.
     */
    private static final String FOOTER = "+------------+--------------+--------------+\n";

    /**
     * Formato de cada fila de la tabla, con alineación adecuada para los valores.
     */
    private static final String ROW_FORMAT = "| %-10s | %-12d | %-12d |\n";

    /**
     * Imprime en la consola una tabla con los resultados del conteo de líneas de código.
     * 
     * @param programName Nombre del programa analizado.
     * @param physicalLOC Número de líneas físicas de código (LOC físicas).
     * @param logicalLOC Número de líneas lógicas de código (LOC lógicas).
     */
    public static void printResults(String programName, int physicalLOC, int logicalLOC) {
        String tableText = buildTable(programName, physicalLOC, logicalLOC);
        System.out.println(tableText);
    }   

    /**
     * Construye la tabla formateada con los resultados del conteo de líneas de código.
     * 
     * @param programName Nombre del programa analizado.
     * @param physicalLOC Número de líneas físicas de código (LOC físicas).
     * @param logicalLOC Número de líneas lógicas de código (LOC lógicas).
     * @return Una cadena de texto representando la tabla con los datos formateados.
     */
    private static String buildTable(String programName, int physicalLOC, int logicalLOC) {
        StringBuilder resultText = new StringBuilder();
        resultText.append(HEADER);
        resultText.append(String.format(ROW_FORMAT, programName, logicalLOC, physicalLOC));
        resultText.append(FOOTER);
        return resultText.toString();
    }
}
