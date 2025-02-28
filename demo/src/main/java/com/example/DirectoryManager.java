package com.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import com.example.exceptions.FileException;
import com.example.exceptions.FileFormatException;

/**
 * The DirectoryManager class is responsible for managing and processing files within a specified directory.
 * It provides functionality to validate the directory, recursively list all files, and process the files
 * to count physical and logical lines.
 */
public class DirectoryManager {
    /**
     * A list to store the paths of all files found in the directory and its subdirectories.
     */
    private List<String> filePaths;

    /**
     * The directory to be managed, represented as a File object.
     */
    private File directory;

    /**
     * Constructs a new DirectoryManager with the specified directory path.
     *
     * @param directoryPath The path of the directory to be managed.
     */
    public DirectoryManager(String directoryPath) {
        this.directory = new File(directoryPath);
        this.filePaths = new ArrayList<>();
    }

    /**
     * Processes the directory by validating it, retrieving all file paths, and counting the physical and logical lines
     * in each file. The results are then printed.
     *
     * @throws FileException If the directory does not exist or is not valid.
     * @throws IOException If an I/O error occurs during file processing.
     */
    public void processDirectory() throws FileException, IOException {
        if (!this.isValidDirectory()) {
            throw new FileException("Error: The directory does not exist.");
        }

        this.filePaths = this.getAllFilePaths();
        List<String> lines = new ArrayList<>();
        PhysicalLineCounter physicalLineCounter = new PhysicalLineCounter();
        LogicalLineCounter logicalLineCounter = new LogicalLineCounter();
        int totalPhysicalLines = 0;
        int totalLogicalLines = 0;

        for (String filePath : filePaths) {
            try {
                lines = FileManager.readLines(filePath);
                FileFormatValidator.isValidFileFormat(filePath, lines);
                totalPhysicalLines += physicalLineCounter.count(lines);
                totalLogicalLines += logicalLineCounter.count(lines);
            } catch (FileFormatException e) {
                System.out.println(e.getMessage());
            }
        }

        String directoryName = this.getDirectoryName();
        ResultPrinter.printResults(directoryName, totalPhysicalLines, totalLogicalLines);
    }

    /**
     * Returns the name of the directory being managed.
     *
     * @return The name of the directory.
     */
    public String getDirectoryName() {
        return this.directory.getName();
    }

    /**
     * Checks if the specified directory is valid.
     * A directory is considered valid if it exists and is indeed a directory.
     *
     * @return {@code true} if the directory exists and is a valid directory;
     *         {@code false} otherwise.
     */
    public boolean isValidDirectory() {
        if (!this.directory.exists() || !this.directory.isDirectory()) {
            return false;
        }
        return true;
    }

    /**
     * Retrieves all file paths within the directory and its subdirectories.
     *
     * @return A list of file paths.
     */
    public List<String> getAllFilePaths() {
        listFilesRecursively(this.directory);
        return this.filePaths;
    }

    /**
     * Recursively lists all files in the specified directory and its subdirectories.
     *
     * @param directory The directory to search for files.
     */
    private void listFilesRecursively(File directory) {
        File[] allFiles = directory.listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                if (file.isFile()) {
                    this.filePaths.add(file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    listFilesRecursively(file);
                }
            }
        }
    }
}