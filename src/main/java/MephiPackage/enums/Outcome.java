package MephiPackage.enums;

public enum Outcome {
    SUCCESS,
    FAILURE,
    PARTIAL,
    UNKNOWN;

    public static Outcome fromString(String value) {
        if (value == null) return UNKNOWN;
        try {
            return Outcome.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}