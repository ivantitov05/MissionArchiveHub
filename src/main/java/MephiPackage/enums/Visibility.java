package MephiPackage.enums;

public enum Visibility {
    VERY_LOW("Очень низкая"),
    LOW("Низкая"),
    MEDIUM("Средняя"),
    HIGH("Высокая"),
    VERY_HIGH("Очень высокая"),
    UNKNOWN("Неизвестно");

    private final String displayName;

    Visibility(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Visibility fromString(String value) {
        if (value == null) return UNKNOWN;

        switch (value.toUpperCase()) {
            case "VERY_LOW": return VERY_LOW;
            case "LOW": return LOW;
            case "MEDIUM": return MEDIUM;
            case "HIGH": return HIGH;
            case "VERY_HIGH": return VERY_HIGH;
            default: return UNKNOWN;
        }
    }

    @Override
    public String toString() {
        return displayName;
    }
}