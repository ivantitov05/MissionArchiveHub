package MephiPackage.enums;

public enum EscalationRisk {
    NONE("Нет риска"),
    LOW("Низкий"),
    MEDIUM("Средний"),
    HIGH("Высокий"),
    CRITICAL("Критический"),
    UNKNOWN("Неизвестно");

    private final String displayName;

    EscalationRisk(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static EscalationRisk fromString(String value) {
        if (value == null) return UNKNOWN;

        switch (value.toUpperCase()) {
            case "NONE": return NONE;
            case "LOW": return LOW;
            case "MEDIUM": return MEDIUM;
            case "HIGH": return HIGH;
            case "CRITICAL": return CRITICAL;
            default: return UNKNOWN;
        }
    }

    @Override
    public String toString() {
        return displayName;
    }
}