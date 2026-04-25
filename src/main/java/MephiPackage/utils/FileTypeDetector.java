package MephiPackage.utils;

import MephiPackage.utils.FileFormat;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileTypeDetector {

    private static final List<TypeHandler> HANDLERS = Arrays.asList(
            new JSONHandler(),
            new XMLHandler(),
            new IniTXTHandler(),
            new StreamHandler(),
            new YAMLHandler(),
            new TXTHandler()
    );

    public static FileFormat checkType(File file) {
        for (TypeHandler handler : HANDLERS) {
            FileFormat result = handler.check(file);
            if (result != null) {
                return result;
            }
        }
        return FileFormat.TXT;
    }
}