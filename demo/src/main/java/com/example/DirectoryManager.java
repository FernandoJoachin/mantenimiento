package com.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import com.example.exceptions.FileException;

/**
 * The DirectoryManager class is responsible for managing and processing files within a specified directory.
 * It provides functionality to validate the directory, recursively list all files, and process the files
 * to count physical and logical lines.
 */
public class DirectoryManager {
    /**
     * A list to store all java files found in the directory and its subdirectories.
     */
    private List<JavaFile> javaFiles;

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
        this.javaFiles = new ArrayList<>();
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

        this.getAllJavaFiles();
        PhysicalLineCounter physicalLineCounter = new PhysicalLineCounter();
        LogicalLineCounter logicalLineCounter = new LogicalLineCounter();
        int totalPhysicalLines = 0;
        int totalLogicalLines = 0;

        for (JavaFile javaFile : this.javaFiles) {
            try {
                FileFormatValidator.isValidFileFormat(javaFile);
                javaFile.setPhysicalLines(physicalLineCounter.count(javaFile));
                javaFile.setLogicalLines(logicalLineCounter.count(javaFile));
                totalPhysicalLines += javaFile.getPhysicalLines();
                totalLogicalLines += javaFile.getLogicalLines();
            } catch (Exception e) {
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
     * Retrieves all Java files within the specified directory and its subdirectories.
     * This method initiates a recursive search starting from the root directory 
     * and collects all files with a `.java` extension.
     *
     * @return A list of {@link JavaFile} objects representing the Java files found.
     * @throws FileException If an error occurs related to file handling (e.g., invalid directory).
     * @throws IOException If an I/O error occurs during the file search process.
     */
    public List<JavaFile> getAllJavaFiles() throws FileException, IOException {
        listFilesRecursively(this.directory);
        return this.javaFiles;
    }

    /**
     * Recursively lists all files in the specified directory and its subdirectories.
     * If a file has a `.java` extension, it is added to the list of Java files.
     *
     * @param directory The directory to search for files. Must not be null.
     * @throws FileException If the directory is invalid or inaccessible.
     * @throws IOException If an I/O error occurs while accessing the directory or its files.
     */
    private void listFilesRecursively(File directory) throws FileException, IOException {
        File[] allFiles = directory.listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                if (file.isFile() && file.getName().endsWith(".java")) {
                    JavaFile javaFile = new JavaFile(file.getAbsolutePath(), file.getName());
                    this.javaFiles.add(javaFile);
                } else if (file.isDirectory()) {
                    listFilesRecursively(file);
                }
            }
        }
    }
}