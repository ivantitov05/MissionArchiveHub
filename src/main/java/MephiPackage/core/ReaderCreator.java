package MephiPackage.core;

import MephiPackage.objects.Mission;
import MephiPackage.readers.Reader;

import java.io.File;
import java.io.IOException;

public abstract class ReaderCreator {
    public abstract Reader createReader();

    public Mission readMission(File file) throws IOException {
        try {
            Reader reader = createReader();
            return reader.extract(file);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            throw e;
        }
    }
}
