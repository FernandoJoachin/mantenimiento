package com.example.constants;

public class JavaRegexConstants {
    public final static String ACCESS_MODIFIERS_REGEX = "((public|private|protected)\\s+)?";
    public final static String DATATYPE_DECLARATION_REGEX = "(\\s*[a-zA-Z0-9]+(<[a-zA-Z0-9]+>)?\\s+)";
    public final static String THROWS_DECLARATION_REGEX = "(\\s+(throws\\s+(\\w+\\s*,\\s*)*\\w+)\\s*)?";
    public final static String PARAMETERS_DECLARATION_REGEX = "(\\([^)]*\\)\\s*)";
    public final static String IDENTIFIER_DECLARATION_REGEX = "\\w+\\s*";
    public final static String STRUCT_DECLARATION_REGEX = "((class|enum|interface)\\s+)";
    public final static String GENERIC_PARAMETERS_REGEX = "(<[^>]+>)?";
    public final static String FLOW_CONTROL_REGEX = "((if|for|while|switch)\\s*\\([^)]*\\))\\s*";
    public final static String WILDCARD_IMPORT_REGEX = "^\\s*import\\s+[a-zA-Z0-9_.]+\\.\\*\\s*;$";
    public final static String FINAL_OR_STATIC_REGEX = "(?:(?:static\\s+)?(?:final\\s+)?|(?:final\\s+)?(?:static\\s+)?)?";
    public final static String TRY_DECLARATION_REGEX = "(try\\s+" + PARAMETERS_DECLARATION_REGEX + "?.*)";
    public static final String METHOD_DECLARATION_REGEX = 
        ACCESS_MODIFIERS_REGEX + 
        FINAL_OR_STATIC_REGEX +          
        DATATYPE_DECLARATION_REGEX +       
        IDENTIFIER_DECLARATION_REGEX +     
        PARAMETERS_DECLARATION_REGEX +     
        THROWS_DECLARATION_REGEX;   
    public static final String STRUCTURE_DECLARATION_REGEX =
        ACCESS_MODIFIERS_REGEX +  
        STRUCT_DECLARATION_REGEX + 
        IDENTIFIER_DECLARATION_REGEX +  
        GENERIC_PARAMETERS_REGEX + 
        THROWS_DECLARATION_REGEX;
}