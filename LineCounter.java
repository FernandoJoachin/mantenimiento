import java.util.List;
import java.util.Arrays;

abstract class LineCounter {

    protected boolean insideBlockComment = false;
    
    /**
     * Verifica si una linea debe contarse como linea fisica.
     *
     * Lo que hace se hace:
     * - Se ignoran las lineas vacias.
     * - Si la linea inicia con un comentario de linea (//) se ignora,
     *   a menos que tenga codigo antes de dicho comentario.
     * - Si se detecta el inicio de un bloque de comentario ("/*") se evalua:
     *   * Si el bloque se cierra en la misma linea, se cuenta la linea
     *        si existe codigo antes o despues del bloque.
     *   * Si no se cierra en la misma linea, se marca que se esta dentro
     *        de un bloque y se verifica si hay codigo antes.
     * - Cuando se esta dentro de un bloque de comentario, se busca el cierre.
     *   Si en la linea de cierre hay codigo posterior, la linea se considera valida.
     *
     * @param line Linea a evaluar.
     * @return true si la linea debe contarse, false en caso contrario.
     */
    protected boolean isValidLine(String line) {
        String trimmed = line.trim();
        
        if (trimmed.isEmpty()) {
            return false;
        }
        
        if (insideBlockComment) {
            int endBlock = line.indexOf("*/");
            if (endBlock != -1) {
                insideBlockComment = false;
                String after = line.substring(endBlock + 2).trim();
                return !after.isEmpty(); // Se cuenta la linea si hay codigo despues del bloque
            }
            return false; // Toda la linea esta dentro del bloque de comentario
        }
        
        int blockStart = line.indexOf("/*");
        int lineComment = line.indexOf("//");
        
        
        if (blockStart != -1) {
            int blockEnd = line.indexOf("*/", blockStart + 2);
            if (blockEnd != -1) {
                // El bloque se cierra en la misma linea.
                String before = line.substring(0, blockStart).trim();
                String after = line.substring(blockEnd + 2).trim();
                return !before.isEmpty() || !after.isEmpty();// Se cuenta la linea si hay codigo despues o despues del bloque
            } else {
                // El bloque no se cierra en esta linea: se marca el estado y se cuenta la parte anterior.
                String before = line.substring(0, blockStart).trim();
                insideBlockComment = true;
                return !before.isEmpty(); // Se cuenta la linea si hay codigo antes del bloque
            }
        }

        if (lineComment != -1) {
            String before = line.substring(0, lineComment).trim();
            return !before.isEmpty(); // Se cuenta si hay codigo antes del "//"
        }
        
        return true;
    }

    /**
     * Elimina los comentarios de una lista de lineas de codigo.
     *
     * @param lines Lista de lineas de codigo original.
     * @return Lista de lineas sin comentarios.
     */
    protected List<String> removeComments(List<String> lines) {
        
        String joinedLines = String.join("\n", lines);// Unimos todas las lineas en un solo String
        joinedLines = joinedLines.replaceAll("(?s)/\\*.*?\\*/", "");  //Eliminamos comentarios en bloque
        joinedLines = joinedLines.replaceAll("//.*(?=\n|$)", "");     //Eliminamos comentarios en linea

        return Arrays.asList(joinedLines.split("\n"));// Volvemos a dividir en lineas y regresamos una lista
    }

    /**
     * Cuenta las lineas segun la implementacion especifica.
     *
     * @param lines Lista de lineas de codigo.
     * @return Numero total de lineas contadas.
     */
    abstract int count(List<String> lines);
}
