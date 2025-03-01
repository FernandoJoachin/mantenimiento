package com.example;

import java.util.Scanner;

import java.io.IOException;
import com.example.exceptions.FileException;

public class App {
    public static void main(String[] args) throws FileException, IOException {
        Scanner scanner = new Scanner(System.in);
        boolean tryAgain = true;

        while (tryAgain) {
            System.out.print("Please enter the directory path: ");
            String directoryPath = scanner.nextLine().trim();
            if (directoryPath.isEmpty()) {
                continue;
            }

            try {
                DirectoryManager directoryManager = new DirectoryManager(directoryPath);
                directoryManager.processDirectory();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.print("Do you want to try analyzing another project? (y/another entry for no): ");
            String userResponse = scanner.nextLine().trim().toLowerCase();
            tryAgain = userResponse.equals("y");
        }

        scanner.close();
    }
}