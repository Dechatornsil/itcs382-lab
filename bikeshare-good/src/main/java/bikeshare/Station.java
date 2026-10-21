package bikeshare;

public enum Station {
    LIBRARY,
    CANTEEN,
    DORM;

    public static Station fromName(String name) {
        return Station.valueOf(name.trim().toUpperCase());
    }
}
