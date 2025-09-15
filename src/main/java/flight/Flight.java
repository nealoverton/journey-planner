package flight;

public class Flight {
    private String targetAirport;
    private int distanceInMiles;

    Flight(String targetAirport, int distanceInMiles) {
        this.targetAirport = targetAirport;
        this.distanceInMiles = distanceInMiles;
    }

    public String getTargetAirport() {
        return targetAirport;
    }

    public int getDistanceInMiles() {
        return distanceInMiles;
    }
}
