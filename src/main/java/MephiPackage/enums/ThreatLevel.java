package MephiPackage.enums;

public enum ThreatLevel {
    SPECIAL_GRADE("Special Grade"),
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low"),
    UNKNOWN("Unknown");

    private final String displayName;

    ThreatLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static ThreatLevel fromString(String value) {
        if (value == null) return UNKNOWN;

        switch (value.toUpperCase()) {
            case "SPECIAL_GRADE":
            case "SPECIAL GRADE":
                return SPECIAL_GRADE;
            case "HIGH":
                return HIGH;
            case "MEDIUM":
                return MEDIUM;
            case "LOW":
                return LOW;
            default:
                return UNKNOWN;
        }
    }

    @Override
    public String toString() {
        return displayName;
    }
}