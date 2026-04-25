package MephiPackage.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class IniTXTHandler extends TypeHandler {

    @Override
    protected MephiPackage.utils.FileFormat check(File file) {
        try {
            String content = Files.readString(file.toPath()).trim();
            if (content.isEmpty()) {
                return null;
            }

            if (content.contains("[MISSION]") && content.contains("missionId=")) {
                return MephiPackage.utils.FileFormat.INITXT;
            }

            return null;
        } catch (IOException e) {
            return null;
        }
    }
}