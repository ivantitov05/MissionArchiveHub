package MephiPackage.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TechniquesType {
    INNATE("INNATE", "Врожденная техника"),
    SHIKIGAMI("SHIKIGAMI", "Техника с шикигами"),
    WEAPON("WEAPON", "Оружейная техника"),
    BARRIER("BARRIER", "Барьерная техника"),
    CURSED_ENERGY("CURSED_ENERGY", "Техника проклятой энергии"),
    DOMAIN("DOMAIN", "Техника домена"),
    HEALING("HEALING", "Исцеляющая техника"),
    BINDING("BINDING", "Связывающая техника"),
    PERCEPTION("PERCEPTION", "Техника восприятия"),
    TELEPORTATION("TELEPORTATION", "Техника телепортации"),
    CURSED_TOOL("CURSED_TOOL", "Проклятый инструмент"),
    RITUAL("RITUAL", "Ритуальная техника"),
    UNKNOWN("UNKNOWN", "Неизвестный тип");

    private final String code;
    private final String description;

    TechniquesType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static TechniquesType fromCode(String code) {
        if (code == null) return UNKNOWN;
        for (TechniquesType type : values()) {
            if (type.code.equals(code) || type.name().equals(code)) {
                return type;
            }
        }
        return UNKNOWN;
    }

    public static TechniquesType fromString(String value) {
        return fromCode(value);
    }

    public static List<String> getAllCodes() {
        return Arrays.stream(values())
                .map(TechniquesType::getCode)
                .collect(Collectors.toList());
    }
}
