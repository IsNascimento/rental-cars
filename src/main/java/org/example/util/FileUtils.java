package org.example.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileUtils {

    public void writeFileIfNotExists(String fileName, List<String> lines) throws IOException {
        var file = new File(fileName);
        throwExceptionIfFileExists(file);
        file.createNewFile();
        try (var writer = new FileWriter(file, true)) {
            for (String line : lines) {
                writer.write(line);
                writer.write(System.lineSeparator());
            }
        }
    }

    private void throwExceptionIfFileExists(File file) throws IOException {
        if (file.exists()) {
            throw new IOException("O arquivo " + file.getAbsolutePath() + " já existe.");
        }
    }
}
