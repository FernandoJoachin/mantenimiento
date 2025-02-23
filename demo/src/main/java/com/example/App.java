package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Iniciando App...");

        String directoryPath = "demo/src/main/java/com/example/files";
        FileManager fileHandler = new FileManager(directoryPath);
        List<String> fileNames = fileHandler.getFileNames();
        if (fileNames.isEmpty()) {
            System.out.println("No hay archivos en el directorio.");
            return;
        }

        List<String> lines = new ArrayList<>();
        boolean isValidFormatFile = true;

        PhysicalLineCounter physicalLineCounter = new PhysicalLineCounter();
        int physicalLOC = 0;
        LogicalLineCounter logicalLineCounter = new LogicalLineCounter();
        int logicalLOC = 0;

        for (String fileName : fileNames) {
           lines = fileHandler.readLines(fileName);
           isValidFormatFile = FileFormatValidator.validateFile(fileName, lines);
           if(!isValidFormatFile){
            continue;
           }
           
           physicalLOC = physicalLineCounter.count(lines);
           logicalLOC = logicalLineCounter.count(lines);
           ResultPrinter.printResults(fileName, physicalLOC, logicalLOC);
        }
    }
}