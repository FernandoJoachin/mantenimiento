package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.exceptions.FileFormatException;

public class App {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);        
        FileManager fileManager = null;
        String directoryPath = "";
        boolean tryAgain = true;
        while(tryAgain) {
            System.out.print("Please enter the directory path: ");
            directoryPath = scanner.nextLine().trim();
            if (directoryPath.isEmpty()) {
                continue;
            }

            fileManager = new FileManager(directoryPath);
            if (!fileManager.isValidDirectory()) {
                System.out.println("Error: The directory does not exist.");
                continue;
            }

            List<String> filePaths = fileManager.getAllFilePaths();
            List<String> lines = new ArrayList<>();

            PhysicalLineCounter physicalLineCounter = new PhysicalLineCounter();
            LogicalLineCounter logicalLineCounter = new LogicalLineCounter();
            int totalPhysicalLines = 0;
            int totalLogicalLines = 0;

            for (String filePath : filePaths) {
                try {
                    lines = fileManager.readLines(filePath);
                    FileFormatValidator.isValidFileFormat(filePath, lines);
                    totalPhysicalLines += physicalLineCounter.count(lines);
                    totalLogicalLines += logicalLineCounter.count(lines);
                } catch (FileFormatException e) {
                    System.out.println(e.getMessage());
                    return;
                }
            }
            String directoryName = fileManager.getDirectoryName();
            ResultPrinter.printResults(directoryName, totalLogicalLines,totalPhysicalLines);

            System.out.print("Do you want to try analyzing another project? (y/another entry for no): ");
            String userResponse = scanner.nextLine().trim().toLowerCase();
            tryAgain = userResponse.equals("y");

        }
        scanner.close();
    }
}