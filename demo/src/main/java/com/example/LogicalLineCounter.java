package com.example;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.constants.JavaRegexConstants;

/**
 * The {@code LogicalLineCounter} class implements the {@code LineCounter} interface
 * to count logical lines of code in a given Java source file.
 * A logical line is determined based on specific Java constructs such as class declarations,
 * method declarations, flow control structures, and try blocks.
 */
public class LogicalLineCounter implements LineCounter {
    /**
     * Regular expression pattern used to match logical line constructs.
     */
    private Pattern pattern;
    /**
     * Matcher for evaluating patterns against each line.
     */
    private Matcher matcher;

    /**
     * Counts the number of logical lines of code in the provided Java file.
     * A logical line is identified based on predefined Java syntax patterns.
     *
     * @param javaFile The Java file to analyze.
     * @return The number of logical lines of code found in the file.
     */
    @Override
    public int count(JavaFile javaFile) {
        List<String> lines = javaFile.getLines();
        int logicalLOC = 0;

        for (String line : lines) {
            if (isLogicalLine(line)) {
                logicalLOC++;
            }
        }
        return logicalLOC;
    }

    /**
     * Determines whether a given line of code qualifies as a logical line.
     * A line is considered logical if it matches any of the predefined patterns
     * for Java structures such as class declarations, method declarations, flow control
     * statements, or try blocks.
     *
     * @param line The line of code to analyze.
     * @return {@code true} if the line is a logical line, otherwise {@code false}.
     */
    private boolean isLogicalLine(String line) {
        line = line.trim();

        this.pattern = Pattern.compile(
            JavaRegexConstants.STRUCT_DECLARATION_REGEX + 
            "|" +
            JavaRegexConstants.METHOD_DECLARATION_REGEX +
            "|" +
            JavaRegexConstants.FLOW_CONTROL_REGEX +
            "|" +
            JavaRegexConstants.TRY_DECLARATION_REGEX
        );

        this.matcher = this.pattern.matcher(line);

        while (this.matcher.find()) {
            return true;
        }
        
        return false;
    }
}