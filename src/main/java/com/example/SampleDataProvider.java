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
            JourneyRequest.fromInput(1, "B30", "D"),
            JourneyRequest.fromInput(2, "A20", "D"),
            JourneyRequest.fromInput(2, "C30", "A"),
            JourneyRequest.fromInput(2, "B10", "C"),
            JourneyRequest.fromInput(5, "B10", "C"),
            JourneyRequest.fromInput(1, "D25", "B"),
            JourneyRequest.fromInput(4, "D40", "A"),
            JourneyRequest.fromInput(2, "B5", "D"),
            JourneyRequest.fromInput(9, "B30", "D")
    );

    public static List<JourneyRequest> getJourneyRequests() {
        return optionalRequests.stream().flatMap(Optional::stream).collect(Collectors.toList());
    }
}
