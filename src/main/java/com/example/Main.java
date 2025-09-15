package com.example;

import com.example.data.SampleDataProvider;
import com.example.flight.Flight;
import com.example.flight.FlightGraphBuilder;
import com.example.journey.JourneyRequest;
import com.example.journey.JourneyResponse;
import com.example.journey.JourneyService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, List<Flight>> flightGraph = FlightGraphBuilder.buildGraph(SampleDataProvider.getFlights());
        JourneyService journeyService = new JourneyService(flightGraph);

        boolean continueInput = true;

        System.out.println("Welcome to Journey Planner!");

        while (continueInput) {
            System.out.print("Enter number of passengers: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input, please enter a number.");
                scanner.next();
            }
            int passengers = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter home to airport journey (e.g. B20): ");
            String homeToAirport = scanner.nextLine().trim();

            System.out.print("Enter destination airport code: ");
            String destinationAirport = scanner.nextLine().trim();

            Optional<JourneyRequest> requestOptional = JourneyRequest.fromInput(passengers, homeToAirport, destinationAirport);

            if (requestOptional.isEmpty()) {
                System.out.println("Invalid input format. Please try again.");
                continue;
            }

            JourneyRequest request = requestOptional.get();
            JourneyResponse response = journeyService.processJourney(request);

            System.out.println("----- Journey Details -----");
            System.out.printf("Passengers: %d%n", request.getPassengers());
            System.out.printf("Home Airport: %s (Distance: %d miles)%n", request.getHomeAirport(), request.getHomeAirportDistanceMiles());
            System.out.printf("Destination Airport: %s%n", request.getDestinationAirport());
            System.out.println("Road Vehicle: " + response.getRoadVehicleName());
            System.out.printf("Road Cost: £%.2f%n%n", response.getRoadCost());
            System.out.println("Outbound Air Route: " + response.getOutboundAirRoute());
            System.out.printf("Outbound Air Cost: £%.2f%n%n", response.getOutboundAirCost());
            System.out.println("Inbound Air Route: " + response.getInboundAirRoute());
            System.out.printf("Inbound Air Cost: £%.2f%n%n", response.getInboundAirCost());
            System.out.printf("Total Cost: £%.2f%n%n", response.getTotalCost());

            System.out.print("Would you like to enter another journey? (y/n): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            continueInput = answer.equals("y") || answer.equals("yes");
        }

        System.out.println("Thank you for using Journey Planner. Goodbye!");
        scanner.close();
    }
}