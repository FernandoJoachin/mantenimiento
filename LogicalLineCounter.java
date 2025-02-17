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
    
    private boolean isLogicalLine(String line){
        return true;
    }
}