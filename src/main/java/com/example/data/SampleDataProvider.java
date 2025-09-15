package com.example.data;

import com.example.journey.JourneyInput;
import com.example.journey.JourneyRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SampleDataProvider {
    private SampleDataProvider() {
        // Hide implicit public constructor
    }

    private static final List<String> flights = List.of(
            "AB800",
            "BC900",
            "CD400",
            "DE400",
            "BF400",
            "CE300",
            "DE300",
            "EB600",
            "CE200",
            "DC700",
            "EB500",
            "FD200"
    );

    public static List<String> getFlights() {
        return flights;
    }

    private static final List<JourneyInput> journeyInputs = List.of(
            new JourneyInput(2, "B20", "D"),
            new JourneyInput(1, "B30", "D"),
            new JourneyInput(2, "A20", "D"),
            new JourneyInput(2, "C30", "A"),
            new JourneyInput(2, "B10", "C"),
            new JourneyInput(5, "B10", "C"),
            new JourneyInput(1, "D25", "B"),
            new JourneyInput(4, "D40", "A"),
            new JourneyInput(2, "B5", "D"),
            new JourneyInput(9, "B30", "D")
    );

    public static List<JourneyInput> getJourneyInputs() {
        return journeyInputs;
    }

    private static final List<Optional<JourneyRequest>> optionalRequests = journeyInputs.stream()
            .map(input -> JourneyRequest.fromInput(input.getPassengers(), input.getHomeAirportJourney(), input.getDestinationAirport()))
            .toList();

    public static List<JourneyRequest> getJourneyRequests() {
        return optionalRequests.stream().flatMap(Optional::stream).collect(Collectors.toList());
    }
}
