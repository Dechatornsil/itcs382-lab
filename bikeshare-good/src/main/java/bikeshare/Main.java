package bikeshare;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RentalService service = new RentalService(new Fleet(), new CsvRideRepository("rides.csv"));
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Commands: rent <memberId> <student|staff> <Library|Canteen|Dorm>, return <memberId> <minutes> <station>, quit");
            while (in.hasNextLine()) {
                String line = in.nextLine().trim();
                if (line.equals("quit")) {
                    break;
                }
                String[] words = line.split(" ");
                if (words[0].equals("rent")) {
                    System.out.println(service.rent(
                            words[1],
                            MemberType.fromName(words[2]),
                            Station.fromName(words[3])));
                } else if (words[0].equals("return")) {
                    System.out.println(service.returnBike(
                            words[1],
                            Integer.parseInt(words[2]),
                            Station.fromName(words[3])));
                }
            }
        }
    }
}
