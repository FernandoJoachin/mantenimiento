import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LineCounterApp {
    public static void main(String[] args) throws IOException {
        System.out.println("Iniciando App...");

        String directoryPath = "files";
        FileHandler fileHandler = new FileHandler(directoryPath);
        List<String> fileNames = fileHandler.getFileNames();
        if (fileNames.isEmpty()) {
            System.out.println("No hay archivos en el directorio.");
            return;
        }

        List<String> lines = new ArrayList<>();
        for (String fileName : fileNames) {
           lines = fileHandler.readLines(fileName);
           //code...
        }
    }
}
