package MephiPackage.enums;

public enum EnemyMobility {
    STATIC("Статичный"),
    SLOW("Медленный"),
    NORMAL("Нормальный"),
    FAST("Быстрый"),
    VERY_FAST("Очень быстрый"),
    TELEPORTING("Телепортирующийся"),
    UNKNOWN("Неизвестно");

    private final String displayName;

    EnemyMobility(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static EnemyMobility fromString(String value) {
        if (value == null) return UNKNOWN;

        switch (value.toUpperCase()) {
            case "STATIC": return STATIC;
            case "SLOW": return SLOW;
            case "NORMAL": return NORMAL;
            case "FAST": return FAST;
            case "VERY_FAST": return VERY_FAST;
            case "TELEPORTING": return TELEPORTING;
            default: return UNKNOWN;
        }
    }

    @Override
    public String toString() {
        return displayName;
    }
}