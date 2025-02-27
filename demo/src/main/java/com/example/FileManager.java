package com.example;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Class that handles file reading operations and listing file names in a directory.
 */
class FileManager {
    private List<String> fileNames;
    private String directory;

    /**
     * Constructor for the FileManager class.
     *
     * @param directory Directory where the files are located.
     */
    public FileManager(String directory) {
        this.directory = directory;
        this.fileNames = new ArrayList<>();
    }

    /**
     * Reads all lines from a specified file.
     *
     *  @param filePath The full path of the file to read.
     * @return A list containing the file's lines. If the file does not exist, 
     * returns an empty list.
     * @throws IOException If an error occurs while reading the file.
     */
    public List<String> readLines(String filePath) throws IOException {
        File file = new File(filePath);
        List<String> lines = new ArrayList<>();
        
        if (!file.exists() || !file.isFile()) {
            System.out.println("The file does not exist: " + file.getName());
            return lines;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    /**
     * Retrieves a list of all file paths in the directory and its subdirectories.
     *
     * @return A list of file paths. If the directory is invalid or does not exist,
     *         an empty list is returned.
     */
    public List<String> getAllFilePaths() {
        File folder = new File(directory);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("The directory does not exist or is invalid.");
            return Collections.emptyList();
        }

        listFilesRecursively(folder);
        return this.fileNames;
    }

    /**
     * Recursively lists all files in the specified folder and its subdirectories.
     *
     * @param folder The folder to search for files.
     */
    private void listFilesRecursively(File folder) {
        File[] allFiles = folder.listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                if (file.isFile()) {
                    this.fileNames.add(file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    listFilesRecursively(file);
                }
            }
        }
    }

    /**
     * Retrieves the name of the directory.
     *
     * @return The name of the directory.
     */
    public String getDirectoryName() {        
        Path path = Paths.get(this.directory);
        return path.getFileName().toString();
    }
}