package hueHarmony.web.model.enums.data_set;

public enum Position {
    EXTERIOR,
    INTERIOR,
    EXTERIOR_AND_INTERIOR;

    public static boolean contains(String value) {
        for (Position myEnum : Position.values()) {
            if (myEnum.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
