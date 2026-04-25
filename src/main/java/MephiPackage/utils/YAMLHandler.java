package MephiPackage.utils;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class YAMLHandler extends TypeHandler {
    private static final YAMLMapper yamlMapper = new YAMLMapper();

    @Override
    protected MephiPackage.utils.FileFormat check(File file) {
        try {
            String content = Files.readString(file.toPath());
            if (content.isEmpty()) {
                return null;
            }

            if (content.matches(".*\\[\\d+\\].*")) {
                return null;
            }

            if (content.contains("\n  - ") || content.contains(":\n    ")) {
                yamlMapper.readTree(file);
                return MephiPackage.utils.FileFormat.YAML;
            }

            return null;
        } catch (IOException e) {
            return null;
        }
    }
}