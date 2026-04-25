package MephiPackage.utils;

import java.io.File;
import java.io.IOException;

class JSONHandler extends TypeHandler {
    @Override
    protected MephiPackage.utils.FileFormat check(File file) {
        try {
            new com.fasterxml.jackson.databind.ObjectMapper().readTree(file);
            return MephiPackage.utils.FileFormat.JSON;
        } catch (IOException e) {
            return null;
        }
    }
}
