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
        // Las siguientes líneas deben o no ser contadas según la lógica:
        // No se cuentan: package, import, declaración de clase.
        lines.add("@Override");
        lines.add("package com.example;");
        lines.add("import java.util.List;");
        lines.add("public class Test {");
        // Se cuentan:
        //  - Sentencia completa (1)
        lines.add("int x = 5;");
        //  - Estructura de control con condición (if) (1)
        lines.add("if (x > 0) {");
        //  - Sentencia for: se cuenta como 3 líneas lógicas
        lines.add("for(int i=0; i<10; i++){");
        //  - Estructura sin condición (else) (1)
        lines.add("else {");
        //  - Sentencia break (1)
        lines.add("break;");
        // Una línea de cierre no cuenta
        lines.add("}");
        
        // El recuento esperado es: 1 + 1 + 3 + 1 + 1 = 7
        int expected = 7;
        int numLogicalLines = counter.count(lines);
        assertEquals(expected, numLogicalLines);
    }
    
    @Test
    public void isLogicalLine() {
        // Una sentencia completa debe contarse:
        assertEquals(1, counter.count(List.of("int x = 5;")));
        // Una declaración de método no se debe contar:
        assertEquals(0, counter.count(List.of("public void metodo() {")));
        // Una línea que solo es una llave tampoco se cuenta:
        assertEquals(0, counter.count(List.of("{")));
        // Una estructura de control (if) debe contarse:
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
        // Una línea que termina en punto y coma se considera una sentencia:
        assertEquals(1, counter.count(List.of("int x = 5;")));
        // Si no tiene punto y coma, no se cuenta:
        assertEquals(0, counter.count(List.of("int x = 5")));
    }
    
    @Test
    public void statementIncomplete() {
        // Simular una sentencia dividida en dos líneas:
        // La primera parte es incompleta y no se debe contar,
        // la segunda la completa y se cuenta.
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
        // Prueba estructuras con condición (if, switch, catch)
        assertEquals(1, counter.count(List.of("if (x > 0) {")));
        assertEquals(1, counter.count(List.of("switch (x) {")));
        assertEquals(1, counter.count(List.of("catch (Exception e) {")));
    }
    
    @Test
    public void isControlStructureWithoutCondition() {
        // Prueba estructuras sin condición (else, do, finally)
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
        // Las líneas "case 1:" y "default:" deben contarse,
        // mientras que "switch (x) {" no.
        assertEquals(1, counter.count(List.of("case 1:")));
        assertEquals(1, counter.count(List.of("default:")));
        assertEquals(0, counter.count(List.of("switch (x) {")));
    }
    
    @Test
    public void isBreakStatement() {
        // La sentencia "break;" se debe contar.
        assertEquals(1, counter.count(List.of("break;")));
        // Si falta el punto y coma no se cuenta.
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
