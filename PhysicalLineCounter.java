import java.util.List;

public class PhysicalLineCounter extends LineCounter {
    /**
     * Counts the physical lines in a list of code lines,
     * removing comments first to count only effective code lines.
     *
     * @param lines List of lines to process.
     * @return Total number of counted physical lines.
     */
    @Override
    int count(List<String> lines) {

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