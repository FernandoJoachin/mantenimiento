import java.util.List;

public class FileFormatValidator {
    private static final int MAX_LINE_LENGTH = 120;

    /**
     * Validates the format of a given Java source file.
     * It checks if the file type is correct and if all lines follow specific formatting rules:
     * - Line length does not exceed the maximum allowed length.
     * - No multiple executable statements in a single line.
     * - Correct usage of brace style.
     *
     * @param fileName The name of the file to validate.
     * @param lines    The content of the file, represented as a list of lines.
     * @return {@code true} if the file follows the formatting rules, {@code false} otherwise.
     */
    public static boolean validateFile(String fileName, List<String> lines) {
        if (!isValidFileType(fileName)) {
            System.out.println("❌ Error: Tipo de archivo no válido -> " + fileName);
            return false;
        }

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();

            if(line.isEmpty()) {
                continue;
            }

            if (!isValidLineLength(line)) {
                System.out.println("⚠️ Advertencia: Línea " + (i + 1) + " del archivo " + fileName + " supera los 120 caracteres.");
                return false;
            }

            if (!isValidBracesStyle(line)) {
                System.out.println("❌ Error: Línea " + (i + 1) + " del archivo " + fileName + " tiene estilo de corchetes incorrecto.");
                return false;
            }

            if (!isValidMultipleStatements(line)) {
                System.out.println("❌ Error: Línea " + (i + 1) + " del archivo " + fileName + " contiene múltiples sentencias ejecutables.");
                return false;
            }

        }

        return true;
    }

    /**
     * Checks if the provided file has a valid Java file extension.
     *
     * @param fileName The name of the file to check.
     * @return {@code true} if the file is a Java file, {@code false} otherwise.
     */
    private static boolean isValidFileType(String fileName) {
        return fileName.endsWith(".java");
    }

    /**
     * Checks if a given line does not exceed the maximum allowed length.
     *
     * @param line       The line of code to check.
     * @return {@code true} if the line length is within the allowed limit, {@code false} otherwise.
     */
    private static boolean isValidLineLength(String line) {
        return line.length() <= MAX_LINE_LENGTH;
    }

    /**
     * Checks if a line of code contains multiple executable statements.
     * The `for` loop structure is allowed since it may contain multiple `;`
     * without being considered separate statements.
     *
     * @param line       The line of code to evaluate.
     * @return {@code true} if the line does not contain multiple statements, {@code false} if it does.
     */
    private static boolean isValidMultipleStatements(String line) {
        boolean isValidForLoopStructure = line.matches("\\s*for\\s*\\(.*;.*;.*\\)\\s*\\{?\\s*");
        
        if (isValidForLoopStructure) {
            return true;
        }
        String stringWithoutQuotes = line.replaceAll("\"[^\"]*\"", "");
        long semicolonCount = stringWithoutQuotes.chars().filter(c -> c == ';').count();

        return semicolonCount <= 1;
    }

    /**
    * Verifies if the line of code has a valid style for opening braces.
    * This means that if the line ends with an opening brace `{`, it must be preceded by a alid declaration of a class, method, or a control structure.
    * @param line The current line of code being evaluated.
    * @return {@code true} if the line is valid or does not end with an opening brace, 
    *         {@code false} if it ends with an opening brace but the previous line is not a valid declaration.
    */
    private static boolean isValidBracesStyle(String line) {
        boolean endsWithOpeningBrace = line.endsWith("{");
        boolean endsWithOpeningAmdClosingBrace = line.endsWith("{}");
        boolean isControlStructureWithSemicolon = line.trim().matches("\\s*(for|while|do|switch|if)\\s*\\(.*\\)\\s*;\\s*");
        boolean isValidDeclarationOrMethod = line.trim().matches(".*\\s*(public|private|protected|class|interface|enum|if|else|for|while|switch|do)\\s+.*\\{.*|.*\\)\\s*\\{.*");

        if(endsWithOpeningAmdClosingBrace || isControlStructureWithSemicolon){ 
            return false;
        }

        if (!endsWithOpeningBrace) {
            return true;
        }

        if(!isValidDeclarationOrMethod) {
            return false;
        }

        return true;
    }
   
}


