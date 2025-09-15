package com.example.journey;

import com.example.Constant;
import com.example.flight.Flight;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class JourneyBuilder {
    private static final int RETURN_JOURNEY_LEGS = 2;
    private final JourneyRequest journeyRequest;
    private boolean includeRoadJourney;
    private boolean includeAirJourney;
    private Map<String, List<Flight>> flightGraph;

    public JourneyBuilder(JourneyRequest journeyRequest) {
        if (journeyRequest == null) {
            throw new IllegalArgumentException("Journey Request cannot be null");
        }
        this.journeyRequest = journeyRequest;
    }

    public JourneyBuilder withRoadJourney() {
        includeRoadJourney = true;
        return this;
    }

    public JourneyBuilder withAirJourney(Map<String, List<Flight>> flightGraph) {
        includeAirJourney = true;
        this.flightGraph = flightGraph;
        return this;
    }

    public JourneyResponse build() {
        validateRequest();
        JourneyResponse journeyResponse = new JourneyResponse();
        if (includeRoadJourney) {
            addRoadJourneyDetails(journeyResponse);
        }
        if (includeAirJourney) {
            addAirJourneyDetails(journeyResponse);
        }
        if (journeyResponse.getRoadCost() == 0 || journeyResponse.getOutboundAirCost() == 0 || journeyResponse.getInboundAirCost() == 0) {
            journeyResponse.setTotalCost(0);
        } else {
            journeyResponse.setTotalCost(journeyResponse.getRoadCost() + journeyResponse.getOutboundAirCost() + journeyResponse.getInboundAirCost());
        }
        return journeyResponse;
    }

    private void addRoadJourneyDetails(JourneyResponse journeyResponse) {
        Optional<RoadVehicle> lowestCostVehicleOptional = Arrays
                .stream(RoadVehicle.values())
                .min(Comparator.comparingDouble(this::calculateRoadJourneyCost));

        RoadVehicle lowestCostVehicle = lowestCostVehicleOptional.orElseThrow(() -> new IllegalStateException("No road vehicles available"));
        journeyResponse.setRoadCost(calculateRoadJourneyCost(lowestCostVehicle));
        journeyResponse.setRoadVehicleName(lowestCostVehicle.getDisplayName());
    }

    private double calculateRoadJourneyCost(RoadVehicle vehicle) {
        double vehiclesRequired = Math.ceil((double) journeyRequest.getPassengers()/vehicle.getMaxPassengers());
        return ((vehicle.getCostPerMileInPounds() * journeyRequest.getHomeAirportDistanceMiles() * RETURN_JOURNEY_LEGS) + vehicle.getParkingFee()) * vehiclesRequired;
    }

    private void addAirJourneyDetails(JourneyResponse journeyResponse) {
        addOutboundAirJourney(journeyResponse);
        addInboundAirJourney(journeyResponse);
    }

    private void addOutboundAirJourney(JourneyResponse journeyResponse) {
        findShortestAirRoute(journeyResponse, journeyRequest.getHomeAirport(), journeyRequest.getDestinationAirport(), journeyRequest.getPassengers(), true);
    }

    private void addInboundAirJourney(JourneyResponse journeyResponse) {
        findShortestAirRoute(journeyResponse, journeyRequest.getDestinationAirport(), journeyRequest.getHomeAirport(), journeyRequest.getPassengers(), false);
    }

    private void findShortestAirRoute(JourneyResponse journeyResponse, String startingAirport, String destinationAirport, Integer passengers, boolean isOutbound) {
        PriorityQueue<Map.Entry<String, Integer>> prioritisedRoutes = new PriorityQueue<>(
                Comparator.comparingInt(Map.Entry::getValue)
        );
        Map<String, Integer> distances = new HashMap<>();
        Map<String, Flight> previousFlights = new HashMap<>();

        for (String airport : flightGraph.keySet()) {
            distances.put(airport, Integer.MAX_VALUE);
            previousFlights.put(airport, null);
        }

        distances.put(startingAirport, 0);
        prioritisedRoutes.offer(new AbstractMap.SimpleEntry<>(startingAirport, 0));

        while (!prioritisedRoutes.isEmpty()) {
            Map.Entry<String, Integer> current = prioritisedRoutes.poll();
            String currentAirport = current.getKey();
            int currentRouteDistance = current.getValue();

            if (currentRouteDistance > distances.get(currentAirport)) continue;

            for (Flight flight : flightGraph.getOrDefault(currentAirport, Collections.emptyList())) {
                int newRouteDistance = currentRouteDistance + flight.getDistanceInMiles();
                String targetAirport = flight.getTargetAirport();
                if (newRouteDistance < distances.getOrDefault(targetAirport, Integer.MAX_VALUE)) {
                    distances.put(targetAirport, newRouteDistance);
                    previousFlights.put(targetAirport, flight);
                    prioritisedRoutes.offer(new AbstractMap.SimpleEntry<>(targetAirport, newRouteDistance));
                }
            }
        }

        List<String> route = new ArrayList<>();
        String current = destinationAirport;
        while (previousFlights.get(current) != null) {
            Flight flight = previousFlights.get(current);
            route.add(0, flight.getName());
            current = flight.getSourceAirport();
        }

        if (isOutbound) {
            if (CollectionUtils.isEmpty(route)) {
               journeyResponse.setOutboundAirRoute(Constant.NO_OUTBOUND_FLIGHT);
               journeyResponse.setOutboundAirCost(0);
            } else {
                journeyResponse.setOutboundAirRoute(String.join("--", route));
                journeyResponse.setOutboundAirCost(calculateAirJourneyCost(distances.get(destinationAirport), passengers));
            }
        } else {
            if (CollectionUtils.isEmpty(route)) {
                journeyResponse.setInboundAirRoute(Constant.NO_INBOUND_FLIGHT);
                journeyResponse.setInboundAirCost(0);
            } else {
                journeyResponse.setInboundAirRoute(String.join("--", route));
                journeyResponse.setInboundAirCost(calculateAirJourneyCost(distances.get(destinationAirport), passengers));
            }
        }
    }

    private double calculateAirJourneyCost(Integer distance, Integer passengers) {
        return distance * Constant.AIR_JOURNEY_COST_PER_MILE_PER_PASSENGER_IN_POUNDS * passengers;
    }

    private void validateRequest() {
        if (StringUtils.isBlank(journeyRequest.getHomeAirport())) {
            throw new IllegalArgumentException("Home airport must be provided");
        }
        if (journeyRequest.getPassengers() == null) {
            throw new IllegalArgumentException("Passengers must not be null");
        }
        if (journeyRequest.getPassengers() <= 0) {
            throw new IllegalArgumentException("Passengers must be greater than zero");
        }

        if (includeAirJourney) {
            if (StringUtils.isBlank(journeyRequest.getDestinationAirport())) {
                throw new IllegalArgumentException("Destination airport must be provided");
            }
        }
    }
}
