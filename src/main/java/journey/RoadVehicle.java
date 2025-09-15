package journey;

public enum RoadVehicle {
    CAR("Car", 4, 0.2, 3),
    TAXI("Taxi", 4, 0.4, 0);

    private final String displayName;
    private final int maxPassengers;
    private final double costPerMileInPounds;
    private final int parkingFee;

    RoadVehicle(String displayName, int maxPassengers, double costPerMileInPounds, int parkingFee) {
        this.displayName = displayName;
        this.maxPassengers = maxPassengers;
        this.costPerMileInPounds = costPerMileInPounds;
        this.parkingFee = parkingFee;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public double getCostPerMileInPounds() {
        return costPerMileInPounds;
    }

    public int getParkingFee() {
        return parkingFee;
    }
}
