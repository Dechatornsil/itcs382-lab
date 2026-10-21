package bikeshare;

public class Bike {
    private final String id;
    private Station station;
    private boolean available;

    public Bike(String id, Station station) {
        this.id = id;
        this.station = station;
        this.available = true;
    }

    public String getId() {
        return id;
    }

    public boolean isAvailableAt(Station station) {
        return available && this.station == station;
    }

    public void rent() {
        available = false;
    }

    public void returnAt(Station station) {
        this.station = station;
        this.available = true;
    }
}
