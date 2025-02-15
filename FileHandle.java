import java.util.List;

class FileHandler {
    private List<String> filenames;
    private String directory;

    public FileHandler(String directory) {
        this.directory = directory;
    }

    public List<String> readLines(String fileName) {
        throw new UnsupportedOperationException("Unimplemented method 'readLines'");
    }

    private boolean checkFiles() {
        throw new UnsupportedOperationException("Unimplemented method 'checkFiles'");
    }

    public List<String> getDirectoryFiles() {
        throw new UnsupportedOperationException("Unimplemented method 'getDirectoryFiles'");
    }
}