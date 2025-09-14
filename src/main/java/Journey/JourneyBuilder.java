package Journey;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class JourneyBuilder {
    private JourneyRequest journeyRequest;
    private boolean includeRoadJourney;
    private boolean includeAirJourney;

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

    public JourneyBuilder withAirJourney() {
        includeAirJourney = true;
        return this;
    }

    public JourneyResponse build() {
        validateRequest();
        JourneyResponse journeyResponse = new JourneyResponse();
        if (includeRoadJourney) {
            addRoadJourneyDetails(journeyResponse);
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
        return ((vehicle.getCostPerMileInPounds() * journeyRequest.getHomeAirportDistanceMiles() * 2) + vehicle.getParkingFee()) * vehiclesRequired;
    }

    private void addAirJourneyDetails(JourneyResponse journeyResponse) {
        PriorityQueue<Map.Entry<String, Integer>> priorityQueue = new PriorityQueue<>(
                Comparator.comparingInt(Map.Entry::getValue)
        );
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

    static class Flight {
        String targetAirport;
        int distanceInMiles;

        Flight(String targetAirport, int distanceInMiles) {
            this.targetAirport = targetAirport;
            this.distanceInMiles = distanceInMiles;
        }
    }
}
