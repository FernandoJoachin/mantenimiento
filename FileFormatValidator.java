import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

public class FileFormatValidator {
    private static final int MAX_LINE_LENGTH = 120;
    
    public static boolean validateFile(String fileName, List<String> lines) {
        if (!isValidFileType(fileName)) {
            System.out.println("❌ Error: Tipo de archivo no válido -> " + fileName);
            return false;
        }

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();

            if (!isValidLineLength(line, i)) {
                System.out.println("⚠️ Advertencia: Línea " + (i + 1) + " supera los 120 caracteres.");
                return false;
            }

            if (!isValidMultipleStatements(line, i)) {
                System.out.println("❌ Error: Línea " + (i + 1) + " contiene múltiples sentencias ejecutables.");
                return false;
            }

            if (!isValidBracesStyle(line, i, lines)) {
                System.out.println("❌ Error: Estilo de corchetes incorrecto en la línea " + (i + 1));
                return false;
            }
        }

        return true;
    }

    private static boolean isValidFileType(String fileName) {
        return fileName.endsWith(".java");
    }

    private static boolean isValidLineLength(String line, int lineNumber) {
        return line.length() <= MAX_LINE_LENGTH;
    }

    private static boolean isValidMultipleStatements(String line, int lineNumber) {
        throw new UnsupportedOperationException("Unimplemented method 'isValidMultipleStatements'");
    }

    private static boolean isValidBracesStyle(String line, int index, List<String> lines) {
        if (line.endsWith("{")) {
            return index > 0 && lines.get(index - 1).trim().matches(".*\\)\\s*\\{");
        }
        return true;
    }
}

