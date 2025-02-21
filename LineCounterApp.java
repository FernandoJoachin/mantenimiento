import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LineCounterApp {
    public static void main(String[] args) throws IOException {
        System.out.println("Iniciando App...");

        String directoryPath = "files";
        FileManager fileHandler = new FileManager(directoryPath);
        List<String> fileNames = fileHandler.getFileNames();
        if (fileNames.isEmpty()) {
            System.out.println("No hay archivos en el directorio.");
            return;
        }
        System.out.println(fileNames);

        List<String> lines = new ArrayList<>();
        Boolean isValidFile = true;
        for (String fileName : fileNames) {
           lines = fileHandler.readLines(fileName);
           isValidFile = FileFormatValidator.validateFile(fileName, lines);
           if(!isValidFile){
            continue;
           }
           //code...
        }
    }
}
