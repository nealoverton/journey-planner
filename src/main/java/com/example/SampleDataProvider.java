package com.example;

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

    private static final List<Optional<JourneyRequest>> optionalRequests = List.of(
            JourneyRequest.fromInput(2, "B20", "D"),
            JourneyRequest.fromInput(2, "B20", "D"),
            JourneyRequest.fromInput(2, "B20", "D"),
            JourneyRequest.fromInput(2, "B20", "D"),
            JourneyRequest.fromInput(2, "B20", "D"),
            JourneyRequest.fromInput(2, "B20", "D"),
            JourneyRequest.fromInput(2, "B20", "D")
    );

    public static List<JourneyRequest> getJourneyRequests() {
        return optionalRequests.stream().flatMap(Optional::stream).collect(Collectors.toList());
    }
}
