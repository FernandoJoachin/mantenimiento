package com.example.constants;

/**
 * This class provides a collection of regular expression constants
 * commonly used for parsing and validating Java code structures.
 * These constants are designed to match various Java language constructs,
 * such as method declarations, class declarations, control flow statements,
 * and more.
 */
public class JavaRegexConstants {
    /**
     * Regular expression to match access modifiers (public, private, protected).
     * Example: "public", "private", "protected".
     */
    public final static String ACCESS_MODIFIERS_REGEX = "((public|private|protected)\\s+)?";

    /**
     * Regular expression to match data type declarations, including generics.
     * Example: "String", "List<String>".
     */
    public final static String DATATYPE_DECLARATION_REGEX = "(\\s*[a-zA-Z0-9]+(<[a-zA-Z0-9]+>)?\\s+)";

    /**
     * Regular expression to match the "throws" clause in method declarations.
     * Example: "throws IOException, SQLException".
     */
    public final static String THROWS_DECLARATION_REGEX = "(\\s+(throws\\s+(\\w+\\s*,\\s*)*\\w+)\\s*)?";

    /**
     * Regular expression to match method or constructor parameters.
     * Example: "(int a, String b)".
     */
    public final static String PARAMETERS_DECLARATION_REGEX = "(\\([^)]*\\)\\s*)";

    /**
     * Regular expression to match identifiers (e.g., variable names, method names).
     * Example: "myVariable", "myMethod".
     */
    public final static String IDENTIFIER_DECLARATION_REGEX = "\\w+\\s*";

    /**
     * Regular expression to match class, enum, or interface declarations.
     * Example: "class MyClass", "interface MyInterface".
     */
    public final static String STRUCT_DECLARATION_REGEX = "((class|enum|interface)\\s+)";

    /**
     * Regular expression to match generic parameters.
     * Example: "<T>", "<String, Integer>".
     */
    public final static String GENERIC_PARAMETERS_REGEX = "(<[^>]+>)?";

    /**
     * Regular expression to match "else if" statements.
     * Example: "else if (condition)".
     */
    public static final String ELSE_IF_REGEX = "\\belse\\s+if\\b";

    /**
     * Regular expression to match flow control statements (if, for, while, switch).
     * Example: "if (condition)", "for (int i = 0; i < 10; i++)".
     */
    public final static String FLOW_CONTROL_REGEX = "((if|for|while|switch)\\s*\\([^)]*\\))\\s*";

    /**
     * Regular expression to match wildcard import statements.
     * Example: "import java.util.*;".
     */
    public final static String WILDCARD_IMPORT_REGEX = "^\\s*import\\s+[a-zA-Z0-9_.]+\\.\\*\\s*;$";

    /**
     * Regular expression to match "final" and/or "static" keywords in any order.
     * Example: "static final", "final static", "static", "final".
     */
    public final static String FINAL_OR_STATIC_REGEX = "(?:(?:static\\s+)?(?:final\\s+)?|(?:final\\s+)?(?:static\\s+)?)?";

    /**
     * Regular expression to match "try" blocks, including optional parameters.
     * Example: "try { ... }", "try (resource) { ... }".
     */
    public final static String TRY_DECLARATION_REGEX = "(try\\s+" + PARAMETERS_DECLARATION_REGEX + "?.*)";

    /**
     * Regular expression to match quoted strings (single or double quotes).
     * Example: "\"Hello, World!\"", "'Hello'".
     */
    public static final String QUOTED_STRING_REGEX = "\"[^\"]*\"|'[^']*'";

    /**
     * Regular expression to match annotations.
     * Example: "@Override", "@SuppressWarnings(\"unchecked\")".
     */
    public static final String ANNOTATION_REGEX = "^\\s*@\\w+(\\(.*\\))?\\s*$";

    /**
     * Regular expression to match method declarations, including access modifiers,
     * return type, method name, parameters, and throws clause.
     * Example: "public static void main(String[] args) throws Exception".
     */
    public static final String METHOD_DECLARATION_REGEX = 
        ACCESS_MODIFIERS_REGEX + 
        FINAL_OR_STATIC_REGEX +          
        DATATYPE_DECLARATION_REGEX +       
        IDENTIFIER_DECLARATION_REGEX +     
        PARAMETERS_DECLARATION_REGEX +     
        THROWS_DECLARATION_REGEX; 

    /**
     * Regular expression to match valid Java declarations, including classes,
     * methods, control flow statements, and more.
     * Example: "public class MyClass { ... }", "if (condition) { ... }".
     */
    public static final String VALID_DECLARATION = ".*\\s*(public|private|protected|class|interface|enum|if|else|for|while|switch|do|try)\\s+.*\\{.*|.*\\)\\s*\\{.*";

    /**
     * Regular expression to match class instantiation with the "new" keyword.
     * Example: "new MyClass()", "new ArrayList<String>()".
     */
    public static final String CLASS_INSTANTIATION_REGEX = ".*\\bnew\\s+(([a-zA-Z0-9_]+\\.)*[a-zA-Z0-9_]+(<[^>]+>)?\\s*\\([^)]*\\)).*";
}