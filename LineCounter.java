import java.util.List;
import java.util.Arrays;

abstract class LineCounter {

    /**
     * Removes comments from a list of code lines.
     *
     * @param lines List of original code lines.
     * @return List of lines without comments.
     */
    protected List<String> removeComments(List<String> lines) {
        
        String joinedLines = String.join("\n", lines);

        joinedLines = joinedLines.replaceAll("(?s)/\\*.*?\\*/", "");  // Removes block comments
        
        joinedLines = joinedLines.replaceAll("//.*(?=\n|$)", "");     // Removes line comments

        return Arrays.asList(joinedLines.split("\n")); // Splits back into lines to return a list
    }

    /**
     * Counts the lines according to the specific implementation.
     *
     * @param lines List of code lines.
     * @return Total number of counted lines.
     */
    abstract int count(List<String> lines);
}