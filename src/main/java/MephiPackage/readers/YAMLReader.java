package MephiPackage.readers;

import MephiPackage.builders.MissionBuilder;
import MephiPackage.builders.MissionBuilderImpl;
import MephiPackage.objects.Mission;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Ридер для YAML формата.
 * Преобразует YAML в Map универсальных ключей.
 */
public class YAMLReader implements Reader {

    private final ObjectMapper yamlMapper;

    public YAMLReader() {
        this.yamlMapper = new YAMLMapper();
    }

    @Override
    public Mission extract(File file) throws IOException {
        Map<String, String> data = new HashMap<>();
        JsonNode root = yamlMapper.readTree(file);

        traverseJson(null, root, data);

        MissionBuilder builder = new MissionBuilderImpl();
        return builder.load(data).build();
    }

    private void traverseJson(String prefix, JsonNode node, Map<String, String> data) {
        if (node == null) return;

        if (node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                String key = entry.getKey();
                JsonNode value = entry.getValue();
                String newPrefix = (prefix == null) ? key : prefix + "." + key;
                traverseJson(newPrefix, value, data);
            });
        } else if (node.isArray()) {
            for (int i = 0; i < node.size(); i++) {
                String newPrefix = prefix + "[" + i + "]";
                traverseJson(newPrefix, node.get(i), data);
            }
        } else if (node.isValueNode()) {
            String value = node.asText();
            if (!value.isEmpty()) {
                data.put(prefix, value);
            }
        }
    }
}