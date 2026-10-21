package bikeshare;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BikeShare {
    public static List<String> bikes = new ArrayList<>();
    public static List<String> locations = new ArrayList<>();
    public static List<String> rentedBy = new ArrayList<>();
    public static int studentCode = 1;
    public static int staffCode = 2;

    public static void main(String[] args) {
        bikes.add("B1");
        bikes.add("B2");
        bikes.add("B3");
        bikes.add("B4");
        bikes.add("B5");
        bikes.add("B6");
        locations.add("Library");
        locations.add("Library");
        locations.add("Canteen");
        locations.add("Canteen");
        locations.add("Dorm");
        locations.add("Dorm");
        rentedBy.add("");
        rentedBy.add("");
        rentedBy.add("");
        rentedBy.add("");
        rentedBy.add("");
        rentedBy.add("");

        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Commands: rent <memberId> <type 1=student 2=staff> <station>, return <memberId> <minutes> <station>, quit");
            while (in.hasNextLine()) {
                String line = in.nextLine().trim();
                if (line.equals("quit")) {
                    break;
                }
                String[] words = line.split(" ");
                handle(words[0].equals("return"), words);
            }
        }
    }

    static void handle(boolean isReturn, String[] words) {
        if (isReturn) {
            String memberId = words[1];
            int minutes = Integer.parseInt(words[2]);
            String station = words[3];
            for (int i = 0; i < bikes.size(); i++) {
                if (rentedBy.get(i).startsWith(memberId + ":")) {
                    int type = Integer.parseInt(rentedBy.get(i).split(":")[1]);
                    String bike = bikes.get(i);
                    locations.set(i, station);
                    rentedBy.set(i, "");
                    int rate = type == staffCode ? 3 : 2;
                    int fee = minutes * rate;
                    System.out.println("Receipt: " + bike + ", " + minutes + " minutes, " + fee + " baht");
                    try (FileWriter writer = new FileWriter("rides.csv", true)) {
                        writer.write(memberId + "," + bike + "," + minutes + "," + fee + "\n");
                    } catch (IOException e) {
                        System.err.println("Could not save ride: " + e.getMessage());
                    }
                    return;
                }
            }
            System.out.println("No rental for " + memberId);
        } else {
            String memberId = words[1];
            int type = Integer.parseInt(words[2]);
            String station = words[3];
            for (int i = 0; i < bikes.size(); i++) {
                if (rentedBy.get(i).isEmpty() && locations.get(i).equals(station)) {
                    rentedBy.set(i, memberId + ":" + type);
                    System.out.println("Rented " + bikes.get(i) + " at " + station);
                    return;
                }
            }
            System.out.println("No bike at " + station);
        }
    }
}
