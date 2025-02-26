package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Starting the App...");

        if(args.length == 0){
            System.out.println("Error: The directory cannot be null");
            return;
        }
        
        String directoryPath = args[0];
        FileManager fileManager = new FileManager(directoryPath);
        List<String> filePaths = fileManager.getAllFilePaths();
        if (filePaths.isEmpty()) {
            return;
        }

        List<String> lines = new ArrayList<>();
        boolean isValidFormatFile = true;

        PhysicalLineCounter physicalLineCounter = new PhysicalLineCounter();
        int totalPhysicalLines = 0;
        LogicalLineCounter logicalLineCounter = new LogicalLineCounter();
        int totalLogicalLines = 0;

        for (String filePath : filePaths) {
           lines = fileManager.readLines(filePath);
           isValidFormatFile = FileFormatValidator.isValidFileFormat(filePath, lines);
           if(!isValidFormatFile){
            continue;
           }
           
           totalPhysicalLines += physicalLineCounter.count(lines);
           totalLogicalLines += logicalLineCounter.count(lines);
        }
        String directoryName = fileManager.getDirectoryName();
        ResultPrinter.printResults(directoryName, totalPhysicalLines, totalLogicalLines);
    }
}