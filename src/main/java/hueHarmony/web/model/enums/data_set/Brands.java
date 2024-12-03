package hueHarmony.web.model.enums.data_set;

public enum Brands {
    DULUX,
    ROBBIALAC,
    NIPPON_PAINT,
    ASIAN_PAINTS,
    KANSAI_PAINT;

    public static boolean contains(String value) {
        for (Brands myEnum : Brands.values()) {
            if (myEnum.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
