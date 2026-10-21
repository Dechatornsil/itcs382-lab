package bikeshare;

import java.util.ArrayList;
import java.util.List;

public class Fleet {
    private final List<Bike> bikes = new ArrayList<>();

    public Fleet() {
        bikes.add(new Bike("B1", Station.LIBRARY));
        bikes.add(new Bike("B2", Station.LIBRARY));
        bikes.add(new Bike("B3", Station.CANTEEN));
        bikes.add(new Bike("B4", Station.CANTEEN));
        bikes.add(new Bike("B5", Station.DORM));
        bikes.add(new Bike("B6", Station.DORM));
    }

    public Bike findAvailable(Station station) {
        for (Bike bike : bikes) {
            if (bike.isAvailableAt(station)) {
                return bike;
            }
        }
        return null;
    }
}
