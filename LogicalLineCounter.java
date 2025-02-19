import java.util.List;

public class LogicalLineCounter extends LineCounter {
    @Override
    public int count(List<String> lines) {
        int numLines = 0;

        for (String line : lines) {
            if(isValidLine(line)){
                if(isLogicalLine(line)){
                    if(isForStructure(line)){
                        numLines+=3;
                    }
                    else if(isStatement(line)){
                        int numStatements = countStatements(line);
                        numLines+=numStatements;
                    }
                    else{
                        numLines++;
                    }
                }
            }
        }

        return numLines;
    }
    
    private boolean isLogicalLine(String line) {
        line = line.trim();
        
        if (isEmptyOrBracket(line) || isTryStatement(line) || isClassOrInterfaceStatement(line) || isMethodStatement(line)) {
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
    
    private boolean isClassOrInterfaceStatement(String line) {
        return line.matches("(class|interface)");
    }

    private boolean isMethodStatement(String line) {
        return line.matches("(public|private)+[a-zA-Z+ _<>]+\\(+.*\\)+ *\\{");
    }

    private boolean isStatement(String line) {
        return line.contains(";");
    }
    
    private boolean isTryStatement(String line) {
        return line.contains("try");
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

    private boolean isForStructure(String line) {
        return line.contains("for");
    }

    private int countStatements(String line){
        int counter = 0;
        int length = line.length();
          for (int i = 0; i < length; i++) 
        {
            if(line.charAt(i) == ';'){
                if (i+1 < length){
                    if(line.charAt(i-1) != '\'' && line.charAt(i+1) != '\''){
                        counter++;
                    }
                }
                else{
                    counter++;
                }
            }
        }
          return counter;
    }
}
