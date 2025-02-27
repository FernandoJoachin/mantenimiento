package com.example;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LogicalLineCounterTest {
    private LogicalLineCounter counter = new LogicalLineCounter();
    
    @Test
    public void count() {
        List<String> lines = new ArrayList<>();

        //lines to not count
        lines.add("@Override");
        lines.add("package com.example;");
        lines.add("import java.util.List;");
        lines.add("public class Test {");

        //lines to count
        lines.add("int x = 5;");
        lines.add("if (x > 0) {");
        lines.add("for(int i=0; i<10; i++){");
        lines.add("else {");
        lines.add("break;");
        lines.add("}");
        
        int expected = 7;
        int numLogicalLines = counter.count(lines);
        assertEquals(expected, numLogicalLines);
    }

    @Test
    public void verifyAnnotationsAreNotCounted() {
        List<String> lines = new ArrayList<>();
        lines.add("@Override");
        lines.add("@interface TypeAnnoDemo{}");
        lines.add("@TestAnnotation(\"testing\")");
        int numLogicalLines = counter.count(lines);
        assertEquals(0, numLogicalLines);
    }
    
    @Test
    public void isLogicalLine() {
        assertEquals(1, counter.count(List.of("int x = 5;")));
        assertEquals(0, counter.count(List.of("public void metodo() {")));
        assertEquals(0, counter.count(List.of("{")));
        assertEquals(1, counter.count(List.of("if (x > 0) {")));
    }
    
    @Test
    public void verifyEmptyLine() {
        List<String> lines = new ArrayList<>();
        lines.add("");
        lines.add("for (");
        lines.add("         ");
        int numLogicalLines = counter.count(lines);
        assertEquals(0, numLogicalLines);
    }
    
    @Test
    public void verifyBracketLine() {
        List<String> lines = new ArrayList<>();
        lines.add("for(int i=0; i<5; i++){");
        lines.add("{");
        lines.add("                {");
        int numLogicalLines = counter.count(lines);
        assertEquals(3, numLogicalLines);
    }
    
    @Test
    public void verifyClassOrInterfaceStatement() {
        List<String> lines = new ArrayList<>();
        lines.add("class Prueba{");
        lines.add("interface Prueba");
        lines.add("String i = ‘interface’;");
    
        int numLogicalLines = counter.count(lines);
        assertEquals(1, numLogicalLines);
    }
    
    @Test
    public void verifyMethodStatement() {
        List<String> lines = new ArrayList<>();
        lines.add("public void metodo (){");
        lines.add("abstract int prueba();");
        lines.add("protected List<String> metodo(String cadena)  {");
        lines.add("private boolean prueba ()");
        lines.add("for(int i=0; i<5; i++){");
        lines.add("for ()");
    
        int numLogicalLines = counter.count(lines);
        assertEquals(3, numLogicalLines);
    }
    
    @Test
    public void isStatement() {
        assertEquals(1, counter.count(List.of("int x = 5;")));
        assertEquals(0, counter.count(List.of("int x = 5")));
    }
    
    @Test
    public void statementIncomplete() {
        List<String> lines = new ArrayList<>();
        lines.add("int x =");
        lines.add("5;");
        int numLogicalLines = counter.count(lines);
        assertEquals(1, numLogicalLines);
    }
    
    @Test
    public void verifyTryStatement() {
        List<String> lines = new ArrayList<>();
        lines.add("try {");
        lines.add("for (");
        lines.add("{try{");
        int numLogicalLines = counter.count(lines);
        assertEquals(0, numLogicalLines);
    }
    
    @Test
    public void isControlStructureWithCondition() {
        assertEquals(1, counter.count(List.of("if (x > 0) {")));
        assertEquals(1, counter.count(List.of("switch (x) {")));
        assertEquals(1, counter.count(List.of("catch (Exception e) {")));
    }
    
    @Test
    public void isControlStructureWithoutCondition() {
        assertEquals(1, counter.count(List.of("else {")));
        assertEquals(1, counter.count(List.of("do {")));
        assertEquals(1, counter.count(List.of("finally {")));
    }
    
    @Test
    public void verifyWhileStatement() {
        List<String> lines = new ArrayList<>();
        lines.add("while (i<5){");
        lines.add("while (");
        lines.add("}while(true);");
        int numLogicalLines = counter.count(lines);
        assertEquals(3, numLogicalLines);
    }
    
    @Test
    public void isSwitchCase() {
        assertEquals(1, counter.count(List.of("case 1:")));
        assertEquals(1, counter.count(List.of("default:")));
        assertEquals(0, counter.count(List.of("switch (x) {")));
    }
    
    @Test
    public void isBreakStatement() {
        assertEquals(1, counter.count(List.of("break;")));
        assertEquals(0, counter.count(List.of("break")));
    }
    
    @Test
    public void verifyImportStatement() {
        List<String> lines = new ArrayList<>();
        lines.add("import libreria.carpeta;");
        lines.add("String i = “import”;");
        lines.add("int i=0;");
        int numLogicalLines = counter.count(lines);
        assertEquals(2, numLogicalLines);
    }
    
    @Test
    public void verifyPackageStatement() {
        List<String> lines = new ArrayList<>();
        lines.add("package carpeta.carpetita;");
        lines.add("String i = “package”;");
        lines.add("import libreria.carpeta;");
        int numLogicalLines = counter.count(lines);
        assertEquals(1, numLogicalLines);
    }

}
