package MephiPackage.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum FileFormat {
    JSON("json", "json"),
    XML("xml", "xml"),
    INITXT("initxt", "txt"),
    OLDTXT("oldtxt", "txt"),
    YAML("yaml", "yaml"),
    TXT("txt", "txt"),
    STREAM("stream", null);

    private final String formatId;
    private final String extension;

    FileFormat(String formatId, String extension) {
        this.formatId = formatId;
        this.extension = extension;
    }

    public String getFormatId() {
        return formatId;
    }

    public String getExtension() {
        return extension == null ? "" : extension;
    }

    public boolean hasExtension() {
        return extension != null && !extension.isEmpty();
    }

    public static List<String> getAllExtensions() {
        return Arrays.stream(values())
                .filter(FileFormat::hasExtension)
                .map(FileFormat::getExtension)
                .distinct()
                .collect(Collectors.toList());
    }

    public static List<String> getAllFormatIds() {
        return Arrays.stream(values())
                .map(FileFormat::getFormatId)
                .collect(Collectors.toList());
    }

    public static FileFormat fromFormatId(String formatId) {
        for (FileFormat format : values()) {
            if (format.formatId.equals(formatId)) {
                return format;
            }
        }
        return TXT;
    }

    public static String getDescription() {
        List<String> extensions = getAllExtensions();
        if (extensions.isEmpty()) {
            return "Файлы миссий";
        }
        return "Файлы миссий (*." + String.join(", *.", extensions) + ")";
    }
}