package hueHarmony.web.model.enums.data_set;

public enum ProductType {

    CLEANER,
    PAINT,
    UNDERCOAT,
    VARNISH,
    WATERPROOFING;

    public static boolean contains(String value) {
        for (ProductType myEnum : ProductType.values()) {
            if (myEnum.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
