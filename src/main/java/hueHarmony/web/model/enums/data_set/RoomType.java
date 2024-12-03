package hueHarmony.web.model.enums.data_set;

public enum RoomType {

    BATHROOM,
    BEDROOM,
    CHILDRENS_ROOM,
    KITCHEN,
    LIVING_ROOM,
    HOME_OFFICE,
    HALLWAY,
    DINING_ROOM;

    public static boolean contains(String value) {
        for (RoomType myEnum : RoomType.values()) {
            if (myEnum.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
