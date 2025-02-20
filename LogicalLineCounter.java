import java.util.List;

public class LogicalLineCounter extends LineCounter {
    
    /** 
     * Counts logical lines
     * 
     * @param lines
     * @return int
     */
    @Override
    public int count(List<String> lines) {
        int numLines = 0;

        List<String> cleanLines = removeComments(lines);
        for (String line : cleanLines) {
            if(isLogicalLine(line)){
                if(isForStatement(line)){
                    numLines+=3;
                }
                else{
                    numLines++;
                }
            }
        }

        return numLines;
    }
    
    
    /**
     * Verifies if it's a logical line
     * 
     * @param line
     * @return boolean
     */
    private boolean isLogicalLine(String line) {
        line = line.trim();
        
        if (isEmptyOrBracket(line) || isTryStatement(line) || isClassOrInterfaceStatement(line) 
            || isMethodStatement(line) || isImportStatement(line)) {
            return false;
        }
        
        if (isStatement(line) || isControlStructure(line) || isSwitchCase(line) || isBreakStatement(line)) {
            return true;
        }
        
        return false;
    }
    
    
    /**
     * Verifies if it is an empty or only bracket line
     * 
     * @param line
     * @return boolean
     */
    private boolean isEmptyOrBracket(String line) {
        return line.isEmpty() || line.equals("{") || line.equals("}");
    }
    
    
    /** 
     * Verifies if it's a class or interface statement
     * 
     * @param line
     * @return boolean
     */
    private boolean isClassOrInterfaceStatement(String line) {
        return line.matches("(class|interface)");
    }

    
    /** 
     * Verifies if it's a method initialization statement
     * 
     * @param line
     * @return boolean
     */
    private boolean isMethodStatement(String line) {
        return line.matches("(public|private)+[a-zA-Z+ _<>]+\\(+.*\\)+ *\\{");
    }

    
    /** 
     * Verifies if it's a general statement
     * 
     * @param line
     * @return boolean
     */
    private boolean isStatement(String line) {
        return line.contains(";");
    }
    
    
    /** 
     * Verifies if it's a try statement
     * 
     * @param line
     * @return boolean
     */
    private boolean isTryStatement(String line) {
        return line.contains("try");
    }
    
    
    /** 
     * Verifies if it's part of a control structure
     * 
     * @param line
     * @return boolean
     */
    private boolean isControlStructure(String line) {
        return line.matches("\\b(if|else|switch|for|while|do|catch|finally)\\b.*");
    }
    
    
    /** 
     * Verifies if it's part of a switch structure
     * 
     * @param line
     * @return boolean
     */
    private boolean isSwitchCase(String line) {
        return line.matches("\\b(case|default)\\b.*:");
    }
    
    
    /** 
     * Verifies if it's a brake statement
     * 
     * @param line
     * @return boolean
     */
    private boolean isBreakStatement(String line) {
        return line.contains("break");
    }

    
    /** 
     * Verifies if it's a for initialization statement
     * 
     * @param line
     * @return boolean
     */
    private boolean isForStatement(String line) {
        return line.contains("for");
    }

    
    /** 
     * Verifies if it's an import statement
     * @param line
     * @return boolean
     */
    private boolean isImportStatement(String line) {
        return line.contains("import");
    }
}
