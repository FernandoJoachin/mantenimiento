import java.io.*;
import java.util.*;
import java.util.regex.*;

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
     * @param fileName Name of the file to read.
     * @return A list containing the file's lines. If the file does not exist, returns an empty list.
     * @throws IOException If an error occurs while reading the file.
     */
    public List<String> readLines(String fileName) throws IOException {
        File file = new File(this.directory, fileName);
        List<String> lines = new ArrayList<>();
        
        if (!file.exists() || !file.isFile()) {
            System.out.println("The file does not exist: " + fileName);
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
     * Retrieves a list of file names in the directory.
     *
     * @return A list of file names in the directory. If the directory is invalid, returns an empty list.
     */
    public List<String> getFileNames() {
        File folder = new File(directory);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("The directory does not exist or is invalid.");
            return Collections.emptyList();
        }

        File[] allFiles = folder.listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                if (file.isFile()) {
                    this.fileNames.add(file.getName());
                }
            }
        }
        return this.fileNames;
    }
}