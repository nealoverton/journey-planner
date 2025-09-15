package journey;

import flight.Flight;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class JourneyBuilder {
    private static final int RETURN_JOURNEY_LEGS = 2;
    private static final double AIR_JOURNEY_COST_PER_MILE = 0.1;
    private JourneyRequest journeyRequest;
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
        calculateOutboundAirJourney(journeyResponse);
        calculateInboundAirJourney(journeyResponse);
    }

    private void calculateOutboundAirJourney(JourneyResponse journeyResponse) {
        calculateAirJourneyCost(journeyResponse, journeyRequest.getHomeAirport(), journeyRequest.getDestinationAirport(), true);
    }

    private void calculateInboundAirJourney(JourneyResponse journeyResponse) {
        calculateAirJourneyCost(journeyResponse, journeyRequest.getDestinationAirport(), journeyRequest.getHomeAirport(), false);
    }

    private void calculateAirJourneyCost(JourneyResponse journeyResponse, String startingAirport, String destinationAirport, boolean isOutbound) {
        PriorityQueue<Map.Entry<String, Integer>> priorityQueue = new PriorityQueue<>(
                Comparator.comparingInt(Map.Entry::getValue)
        );
        Map<String, Integer> distances = new HashMap<>();
        Map<String, Flight> previousFlights = new HashMap<>();

        for (String vertex : flightGraph.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
            previousFlights.put(vertex, null);
        }
        distances.put(startingAirport, 0);

        priorityQueue.offer(new AbstractMap.SimpleEntry<>(startingAirport, 0));

        while (!priorityQueue.isEmpty()) {
            Map.Entry<String, Integer> current = priorityQueue.poll();
            String currentNode = current.getKey();
            int currentDist = current.getValue();

            if (currentDist > distances.get(currentNode)) continue;

            for (Flight flight : flightGraph.getOrDefault(currentNode, Collections.emptyList())) {
                int newDistance = currentDist + flight.getDistanceInMiles();
                String targetAirport = flight.getTargetAirport();
                if (newDistance < distances.getOrDefault(targetAirport, Integer.MAX_VALUE)) {
                    distances.put(targetAirport, newDistance);
                    previousFlights.put(targetAirport, flight);
                    priorityQueue.offer(new AbstractMap.SimpleEntry<>(targetAirport, newDistance));
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
            journeyResponse.setOutboundAirRoute(String.join("--", route));
            journeyResponse.setOutboundAirCost(distances.get(destinationAirport) * AIR_JOURNEY_COST_PER_MILE * RETURN_JOURNEY_LEGS);
        } else {
            journeyResponse.setInboundAirRoute(String.join("--", route));
            journeyResponse.setInboundAirCost(distances.get(destinationAirport) * AIR_JOURNEY_COST_PER_MILE * RETURN_JOURNEY_LEGS);
        }
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
