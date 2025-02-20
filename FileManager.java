import java.io.*;
import java.util.*;
import java.util.regex.*;
/**
 * Clase que maneja operaciones de lectura de archivos y listado de nombres de archivos en un directorio.
 */
class FileManager {
    private List<String> fileNames;
    private String directory;

    /**
     * Constructor de la clase FileHandler.
     *
     * @param directory Directorio donde se encuentran los archivos.
     */
    public FileManager(String directory) {
        this.directory = directory;
        this.fileNames = new ArrayList<>();
    }

    /**
     * Lee todas las líneas de un archivo especificado.
     *
     * @param fileName Nombre del archivo a leer.
     * @return Una lista con las líneas del archivo. Si el archivo no existe, retorna una lista vacía.
     * @throws IOException Si ocurre un error de lectura del archivo.
     */
    public List<String> readLines(String fileName) throws IOException {
        File file = new File(this.directory, fileName);
        List<String> lines = new ArrayList<>();
        
        if (!file.exists() || !file.isFile()) {
            System.out.println("El archivo no existe: " + fileName);
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
     * Obtiene una lista de los nombres de los archivos en el directorio.
     *
     * @return Una lista con los nombres de los archivos en el directorio. Si el directorio no es válido, retorna una lista vacía.
     */
    public List<String> getFileNames() {
        File folder = new File(directory);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("El directorio no existe o no es válido.");
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
