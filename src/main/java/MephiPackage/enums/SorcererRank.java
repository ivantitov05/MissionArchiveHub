package MephiPackage.enums;

public enum SorcererRank {
    UNKNOWN,
    SPECIAL_SPECIAL_GRADE, //IT'S ME
    SPECIAL_GRADE,
    GRADE_1,
    SPECIAL_GRADE_1,
    SEMI_GRADE_1,
    GRADE_2,
    GRADE_3,
    GRADE_4;

    public static SorcererRank fromString(String value) {
        if (value == null) return UNKNOWN;
        try {
            return SorcererRank.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
