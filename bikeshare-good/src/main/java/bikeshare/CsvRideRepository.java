package bikeshare;

import java.io.FileWriter;
import java.io.IOException;

public class CsvRideRepository implements RideRepository {
    private final String path;

    public CsvRideRepository(String path) {
        this.path = path;
    }

    @Override
    public void save(Ride ride) {
        try (FileWriter writer = new FileWriter(path, true)) {
            writer.write(ride.toCsv() + "\n");
        } catch (IOException e) {
            System.err.println("Could not save ride: " + e.getMessage());
        }
    }
}
