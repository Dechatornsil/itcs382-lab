package bikeshare;

import java.util.HashMap;
import java.util.Map;

public class RentalService {
    private final Fleet fleet;
    private final RideRepository rides;
    private final Map<String, OpenRental> open = new HashMap<>();

    public RentalService(Fleet fleet, RideRepository rides) {
        this.fleet = fleet;
        this.rides = rides;
    }

    public String rent(String memberId, MemberType type, Station station) {
        if (open.containsKey(memberId)) {
            return "Already renting";
        }
        Bike bike = fleet.findAvailable(station);
        if (bike == null) {
            return "No bike at " + station;
        }
        bike.rent();
        open.put(memberId, new OpenRental(type, bike));
        return "Rented " + bike.getId() + " at " + station;
    }

    public String returnBike(String memberId, int minutes, Station station) {
        OpenRental current = open.remove(memberId);
        if (current == null) {
            return "No rental for " + memberId;
        }
        current.bike.returnAt(station);
        Ride ride = new Ride(memberId, current.type, current.bike, minutes);
        rides.save(ride);
        return ride.receipt();
    }

    private static class OpenRental {
        private final MemberType type;
        private final Bike bike;

        private OpenRental(MemberType type, Bike bike) {
            this.type = type;
            this.bike = bike;
        }
    }
}
