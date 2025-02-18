import java.util.List;

public class LogicalLineCounter extends LineCounter {
    @Override
    int count(List<String> lines) {
        int numLines = 0;

        for (String line : lines) {
            if(isValidLine(line)){
                if(isLogicalLine(line)){
                    numLines++;
                }
            }
        }

        return numLines;
    }
    
    private boolean isLogicalLine(String line) {
        line = line.trim();
        
        if (isEmptyOrBracket(line) || isSingleLineComment(line) || isBlockComment(line)) {
            return false;
        }
        
        if (isStatement(line) || isControlStructure(line) || isSwitchCase(line) || isBreakStatement(line)) {
            return true;
        }
        
        return false;
    }
    
    private boolean isEmptyOrBracket(String line) {
        return line.isEmpty() || line.equals("{") || line.equals("}");
    }
    
    private boolean isSingleLineComment(String line) {
        return line.startsWith("//");
    }
    
    private boolean isBlockComment(String line) {
        return line.startsWith("/*") || line.endsWith("*/");
    }
    
    private boolean isStatement(String line) {
        return line.contains(";");
    }
    
    private boolean isControlStructure(String line) {
        return line.matches("\\b(if|else|switch|for|while|do|catch|finally)\\b.*");
    }
    
    private boolean isSwitchCase(String line) {
        return line.matches("\\b(case|default)\\b.*:");
    }
    
    private boolean isBreakStatement(String line) {
        return line.contains("break");
    }

}
