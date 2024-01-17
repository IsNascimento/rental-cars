package org.example.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileUtils {

    public static void writeFile(String fileName, List<String> lines) throws IOException {
        var file = new File(fileName);
        createDirectory(file.getParent()); // Verificar e criar o diretório se necessário
        file.createNewFile();
        try (var writer = new FileWriter(file, false)) {
            for (String line : lines) {
                writer.write(line);
                writer.write(System.lineSeparator());
            }
        }
    }

    public static void createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}