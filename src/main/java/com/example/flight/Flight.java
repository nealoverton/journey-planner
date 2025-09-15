package com.example.flight;

public class Flight {
    private String name;
    private String sourceAirport;
    private String targetAirport;
    private int distanceInMiles;

    Flight(String name, String sourceAirport, String targetAirport, int distanceInMiles) {
        this.name = name;
        this.sourceAirport = sourceAirport;
        this.targetAirport = targetAirport;
        this.distanceInMiles = distanceInMiles;
    }

    public String getName() {
        return name;
    }

    public String getSourceAirport() {
        return sourceAirport;
    }

    public String getTargetAirport() {
        return targetAirport;
    }

    public int getDistanceInMiles() {
        return distanceInMiles;
    }
}
