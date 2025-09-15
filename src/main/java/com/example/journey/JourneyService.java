package com.example.journey;

import com.example.flight.Flight;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class JourneyService {
    private static final Logger LOGGER = Logger.getLogger(JourneyService.class.getName());

    private final Map<String, List<Flight>> flightGraph;

    public JourneyService(Map<String, List<Flight>> flightGraph) {
        this.flightGraph = flightGraph;
    }

    public JourneyResponse processJourney(JourneyRequest journeyRequest) {
        JourneyBuilder journeyBuilder = new JourneyBuilder(journeyRequest)
                .withRoadJourney()
                .withAirJourney(flightGraph);

        return journeyBuilder.build();
    }

    public List<JourneyResponse> processJourneyList(List<JourneyRequest> journeyRequests) {
        List<JourneyResponse> responses = new ArrayList<>();
        for (JourneyRequest journeyRequest : journeyRequests) {
            try {
                responses.add(processJourney(journeyRequest));
            } catch (IllegalArgumentException exception) {
                LOGGER.severe("Failed to process journey request: " + journeyRequest.toString());
                LOGGER.severe(exception.getMessage());
            }
        }
        return responses;
    }
}
