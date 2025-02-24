package com.example;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LogicalLineCounterTest {
    private LogicalLineCounter counter = new LogicalLineCounter();
    
    @Test
    public void count() {
    }

    @Test
    public void isLogicalLine() {
    }

    @Test
    public void verifyEmptyLine() {
        List<String> lines = new ArrayList<>();
        lines.add("");
        lines.add("for (");
        lines.add("         ");

        int numLogicalLines = counter.count(lines);
        assertEquals(0,numLogicalLines);
    }

    @Test
    public void verifyBracketLine() {
        List<String> lines = new ArrayList<>();
        lines.add("for(int i=0; i<5; i++){");
        lines.add("{");
        lines.add("                {");

        int numLogicalLines = counter.count(lines);
        assertEquals(3,numLogicalLines);
    }

    @Test
    public void verifyClassOrInterfaceStatement() {
        List<String> lines = new ArrayList<>();
        lines.add("class Prueba{");
        lines.add("interface Prueba");
        lines.add("String i = ‘interface’;");

        int numLogicalLines = counter.count(lines);
        assertEquals(1,numLogicalLines);
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
        assertEquals(3,numLogicalLines);
    }

    @Test
    public void isStatement() {
    }

    @Test
    public void statementIncomplete() {
    }

    @Test
    public void verifyTryStatement() {
        List<String> lines = new ArrayList<>();
        lines.add("try {");
        lines.add("for (");
        lines.add("{try{");

        int numLogicalLines = counter.count(lines);
        assertEquals(0,numLogicalLines);
    }

    @Test
    public void isControlStructureWithCondition() {
    }

    @Test
    public void isControlStructureWithoutCondition() {
    }

    @Test
    public void verifyWhileStatement() {
        List<String> lines = new ArrayList<>();
        lines.add("while (i<5){");
        lines.add("while (");
        lines.add("}while(true);");

        int numLogicalLines = counter.count(lines);
        assertEquals(3,numLogicalLines);
    }

    @Test
    public void isSwitchCase() {
    }

    @Test
    public void isBreakStatement() {
    }

    @Test
    public void verifyForStatement() {
        List<String> lines = new ArrayList<>();
        lines.add("for(int i=0; i<5; i++){");
        lines.add("for(");
        lines.add("int i=0;");

        int numLogicalLines = counter.count(lines);
        assertEquals(4,numLogicalLines);
    }

    @Test
    public void verifyImportStatement() {
        List<String> lines = new ArrayList<>();
        lines.add("import libreria.carpeta;");
        lines.add("String i = “import”;");
        lines.add("int i=0;");

        int numLogicalLines = counter.count(lines);
        assertEquals(2,numLogicalLines);
    }

    @Test
    public void verifyPackageStatement() {
        List<String> lines = new ArrayList<>();
        lines.add("package carpeta.carpetita;");
        lines.add("String i = “package”;");
        lines.add("import libreria.carpeta;");

        int numLogicalLines = counter.count(lines);
        assertEquals(1,numLogicalLines);
    }
}
