package MephiPackage.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class StreamHandler extends TypeHandler {
    @Override
    protected MephiPackage.utils.FileFormat check(File file) {
        try {
            String content = Files.readString(file.toPath()).trim();
            if (content.startsWith("MISSION_CREATED")) {
                return MephiPackage.utils.FileFormat.STREAM;
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}