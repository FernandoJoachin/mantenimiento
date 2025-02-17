import java.util.List;

public class PhysicalLineCounter extends LineCounter {
    /**
     * Cuenta las lineas fisicas en una lista de lineas de codigo.
     * Se recorre cada linea, utilizando el metodo isValidLine heredado para determinar
     * si la linea contiene codigo.
     *
     * @param lines Lista de lineas a procesar.
     * @return Numero total de lineas fisicas contadas.
     */
    @Override
    int count(List<String> lines) {
        insideBlockComment = false;
        int count = 0;

        for (String line : lines) {
            if (isValidLine(line)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Cuenta las lineas fisicas en una lista de lineas de codigo
     * removiendo los comentarios primero para que solo cuente lineas de codigo efectivas.
     *
     * @param lines Lista de lineas a procesar.
     * @return Numero total de lineas fisicas contadas.
     */
    public int count2(List<String> lines) {

        List<String> linesWithoutComments  = removeComments(lines);

        int count = 0;
        for (String line : linesWithoutComments ) {
            if (!line.trim().isEmpty()) {
                count++;
            }
        }

        return count;
    }
    
}