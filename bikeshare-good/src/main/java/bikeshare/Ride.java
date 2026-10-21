package bikeshare;

public class Ride {
    private final String memberId;
    private final MemberType memberType;
    private final Bike bike;
    private final int minutes;
    private final int fee;

    public Ride(String memberId, MemberType memberType, Bike bike, int minutes) {
        this.memberId = memberId;
        this.memberType = memberType;
        this.bike = bike;
        this.minutes = minutes;
        this.fee = memberType.feeFor(minutes);
    }

    public String receipt() {
        return "Receipt: " + bike.getId() + ", " + minutes + " minutes, " + fee + " baht";
    }

    public String toCsv() {
        return memberId + "," + memberType + "," + bike.getId() + "," + minutes + "," + fee;
    }
}
