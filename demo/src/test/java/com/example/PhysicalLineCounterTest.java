package com.example;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class PhysicalLineCounterTest {
    
    @Test
    public void testCountWithOnlyCode() {
        PhysicalLineCounter counter = new PhysicalLineCounter();
        List<String> lines = Arrays.asList(
            "int a = 0;",
            "a++;",
            "System.out.println(a);"
        );
        int expected = 3;
        int actual = counter.count(lines);
        assertEquals("Expected count for only code lines should be 3", expected, actual);
    }

    @Test
    public void testCountWithOnlyComments() {
        PhysicalLineCounter counter = new PhysicalLineCounter();
        List<String> lines = Arrays.asList(
            "// Line comment",
            " ",
            "/* Block comment */",
            ""
        );
        int expected = 0;
        int actual = counter.count(lines);
        assertEquals("Expected count for file with only comments and empty lines should be 0", expected, actual);
    }

    @Test
    public void testCountWithMixedInput() {
        PhysicalLineCounter counter = new PhysicalLineCounter();
        List<String> lines = Arrays.asList(
            "/* Start block comment",
            "Block comment",
            "End block comment */",
            "int a = 5; // assignment",
            " ",
            "System.out.println(a);"
        );
        int expected = 2;
        int actual = counter.count(lines);
        assertEquals("Expected count for mixed input should be 2 (only counting effective code lines)", expected, actual);
    }
}
