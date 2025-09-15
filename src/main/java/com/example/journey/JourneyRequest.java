package com.example.journey;

import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JourneyRequest {
    private static final Logger LOGGER = Logger.getLogger(JourneyRequest.class.getName());
    private final Integer passengers;
    private final Integer homeAirportDistanceMiles;
    private final String homeAirport;
    private final String destinationAirport;

    private JourneyRequest(Integer passengers, Integer homeAirportDistanceMiles, String homeAirport, String destinationAirport) {
        this.passengers = passengers;
        this.homeAirportDistanceMiles = homeAirportDistanceMiles;
        this.homeAirport = homeAirport;
        this.destinationAirport = destinationAirport;
    }

    public static Optional<JourneyRequest> fromInput(Integer passengers, String homeAirportJourney, String destinationAirport) {
        Pattern homeAirportJourneyPattern = Pattern.compile("^([A-Z]+)(\\d+)$");
        Matcher homeAirportJourneyMatcher = homeAirportJourneyPattern.matcher(homeAirportJourney);
        if (homeAirportJourneyMatcher.matches()) {
            try {
                String homeAirport = homeAirportJourneyMatcher.group(1);
                Integer homeAirportDistanceMiles = Integer.parseInt(homeAirportJourneyMatcher.group(2));
                return Optional.of(new JourneyRequest(passengers, homeAirportDistanceMiles, homeAirport, destinationAirport));
            } catch (NumberFormatException exception) {
                LOGGER.severe("Couldn't parse home airport distance from: " + homeAirportJourneyMatcher.group(2));
                return Optional.empty();
            }
        }
        LOGGER.severe("Couldn't parse home airport journey from: " + homeAirportJourney);
        return Optional.empty();
    }

    public Integer getPassengers() {
        return passengers;
    }

    public Integer getHomeAirportDistanceMiles() {
        return homeAirportDistanceMiles;
    }

    public String getHomeAirport() {
        return homeAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }
}
