package com.example.journey;

public class JourneyInput {
    private final Integer passengers;
    private final String homeAirportJourney;
    private final String destinationAirport;

    public JourneyInput(Integer passengers, String homeAirportJourney, String destinationAirport) {
        this.passengers = passengers;
        this.homeAirportJourney = homeAirportJourney;
        this.destinationAirport = destinationAirport;
    }

    public Integer getPassengers() {
        return passengers;
    }

    public String getHomeAirportJourney() {
        return homeAirportJourney;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }
}
