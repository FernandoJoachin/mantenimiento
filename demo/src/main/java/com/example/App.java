package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.exceptions.FileFormatException;

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

        PhysicalLineCounter physicalLineCounter = new PhysicalLineCounter();
        int totalPhysicalLines = 0;
        LogicalLineCounter logicalLineCounter = new LogicalLineCounter();
        int totalLogicalLines = 0;

        for (String filePath : filePaths) {
            try {
                lines = fileManager.readLines(filePath);
                FileFormatValidator.isValidFileFormat(filePath, lines);
                totalPhysicalLines += physicalLineCounter.count(lines);
                totalLogicalLines += logicalLineCounter.count(lines);
            } catch (FileFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        String directoryName = fileManager.getDirectoryName();
        ResultPrinter.printResults(directoryName, totalPhysicalLines, totalLogicalLines);
    }
}