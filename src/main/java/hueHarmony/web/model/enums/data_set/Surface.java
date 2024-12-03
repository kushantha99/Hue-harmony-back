package hueHarmony.web.model.enums.data_set;

public enum Surface {

    BLUESTONE,
    DOORS,
    FURNITURE,
    METAL,
    WALLS,
    WINDOWS,
    WOOD;


    public static boolean contains(String value) {
        for (Surface myEnum : Surface.values()) {
            if (myEnum.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
