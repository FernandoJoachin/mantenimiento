import java.util.List;

public class LogicalLineCounter extends LineCounter {
    
    /** 
     * Counts logical lines
     * 
     * @param lines
     * @return int
     */
    @Override
    int count(List<String> lines) {
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
            || isMethodStatement(line) || isImportStatement(line) || isPackageStatement(line)) {
            return false;
        }
        
        if (isForStatement(line) || isSwitchCase(line) || isControlStructureWithCondition(line)
            || isControlStructureWithoutCondition(line) || isWhileStatement(line) || isBreakStatement(line)
            || isStatement(line)) {
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
        return line.trim().isEmpty() || line.trim().equals("{") || line.trim().equals("}");
    }

    /** 
     * Verifies if it's a class or interface statement
     * 
     * @param line
     * @return boolean
     */
    private boolean isClassOrInterfaceStatement(String line) {
        return line.trim().matches("^(public|private|protected|)+ *(class|interface)+.*\\{");
    }

    /** 
     * Verifies if it's a method initialization statement
     * 
     * @param line
     * @return boolean
     */
    private boolean isMethodStatement(String line) {
        return line.trim().matches("^(public|private|abstract|protected)+[a-zA-Z+ _<>]+\\(+.*");
    }

    /** 
     * Verifies if it's a general statement
     * 
     * @param line
     * @return boolean
     */
    private boolean isStatement(String line) {
        return line.matches(".*;$");
    }
    
    /** 
     * Verifies if it's a try statement
     * 
     * @param line
     * @return boolean
     */
    private boolean isTryStatement(String line) {
        return line.trim().matches("^try+ *{$");
    }
    
    /** 
     * Verifies if it's part of a control structure that specyfies a contition
     * 
     * @param line
     * @return boolean
     */
    private boolean isControlStructureWithCondition(String line) {
        return line.trim().matches("^(if|switch|catch)+ *\\(+.*");
    }

    /** 
     * Verifies if it's part of a control structure that doesn't hace a specyfied condition
     * 
     * @param line
     * @return boolean
     */
    private boolean isControlStructureWithoutCondition(String line) {
        return line.trim().matches("^(else|do|finally)+ *\\{");
    }

    /** 
     * Verifies if it's a while statement
     * 
     * @param line
     * @return boolean
     */
    private boolean isWhileStatement(String line) {
        return (line.trim().matches("^while+ *\\(+.*")
            || line.trim().matches("^\\}+ *while+ *\\(+.*"));
    }
    
    /** 
     * Verifies if it's part of a switch structure
     * 
     * @param line
     * @return boolean
     */
    private boolean isSwitchCase(String line) {
        return line.trim().matches("^\\b(case|default)\\b.*:");
    }
    
    /** 
     * Verifies if it's a brake statement
     * 
     * @param line
     * @return boolean
     */
    private boolean isBreakStatement(String line) {
        return line.trim().matches("^break+ *;");
    }
    
    /** 
     * Verifies if it's a for initialization statement
     * 
     * @param line
     * @return boolean
     */
    private boolean isForStatement(String line) {
        return line.trim().matches("^for+ *\\(+.*\\)+ *\\{");
    }
    
    /** 
     * Verifies if it's an import statement
     * @param line
     * @return boolean
     */
    private boolean isImportStatement(String line) {
        return line.trim().matches("^import+.*;");
    }

    /** 
     * Verifies if it's a package statement
     * @param line
     * @return boolean
     */
    private boolean isPackageStatement(String line) {
        return line.trim().matches("^package+.*;");
    }
}