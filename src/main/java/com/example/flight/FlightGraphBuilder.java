package com.example.flight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class FlightGraphBuilder {
    private static final Logger LOGGER = Logger.getLogger(FlightGraphBuilder.class.getName());

    public static Map<String, List<Flight>> buildGraph(List<String> flights) {
        Map<String, List<Flight>> graph = new HashMap<>();

        for (String flight : flights) {
            try {
                String from = flight.substring(0, 1);
                String to = flight.substring(1, 2);
                int distance = Integer.parseInt(flight.substring(2));

                graph.computeIfAbsent(from, key -> new ArrayList<>())
                        .add(new Flight(flight, from, to, distance));
            } catch (NumberFormatException exception) {
                LOGGER.severe("Skipping invalid flight data: " + flight);
            }
        }
        return graph;
    }
}
