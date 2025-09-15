package com.example.unit;

import com.example.SampleDataProvider;
import com.example.flight.Flight;
import com.example.flight.FlightGraphBuilder;
import com.example.journey.JourneyBuilder;
import com.example.journey.JourneyRequest;
import com.example.journey.JourneyResponse;
import com.example.journey.RoadVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JourneyBuilderTest {
    private JourneyRequest mockRequest;

    @BeforeEach
    void setUp() {
        mockRequest = Mockito.mock(JourneyRequest.class);
    }

    @Test
    @DisplayName("Can't be instantiated with null JourneyRequest")
    public void testUnsuccessfulNullRequestInstantiation() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new JourneyBuilder(null));
        assertEquals("Journey Request cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Calculates correct car journey details from valid request")
    public void testCalculatesCarJourney() {
        Mockito.when(mockRequest.getPassengers()).thenReturn(2);
        Mockito.when(mockRequest.getHomeAirport()).thenReturn("B");
        Mockito.when(mockRequest.getHomeAirportDistanceMiles()).thenReturn(20);

        JourneyResponse journeyResponse = new JourneyBuilder(mockRequest)
                .withRoadJourney()
                .build();
        assertEquals(RoadVehicle.CAR.getDisplayName(), journeyResponse.getRoadVehicleName());
        assertEquals(11.00, journeyResponse.getRoadCost());
        assertNull(journeyResponse.getOutboundAirRoute());
    }

    @Test
    @DisplayName("Calculates correct multiple car journey details when passengers greater than car capacity")
    public void testCalculatesMultipleCarJourney() {
        Mockito.when(mockRequest.getPassengers()).thenReturn(5);
        Mockito.when(mockRequest.getHomeAirport()).thenReturn("B");
        Mockito.when(mockRequest.getHomeAirportDistanceMiles()).thenReturn(20);

        JourneyResponse journeyResponse = new JourneyBuilder(mockRequest)
                .withRoadJourney()
                .build();
        assertEquals(RoadVehicle.CAR.getDisplayName(), journeyResponse.getRoadVehicleName());
        assertEquals(22.00, journeyResponse.getRoadCost());
        assertNull(journeyResponse.getOutboundAirRoute());
    }

    @Test
    @DisplayName("Calculates correct taxi journey details from valid request")
    public void testCalculatesTaxiJourney() {
        Mockito.when(mockRequest.getPassengers()).thenReturn(2);
        Mockito.when(mockRequest.getHomeAirport()).thenReturn("B");
        Mockito.when(mockRequest.getHomeAirportDistanceMiles()).thenReturn(5);

        JourneyResponse journeyResponse = new JourneyBuilder(mockRequest)
                .withRoadJourney()
                .build();
        assertEquals(RoadVehicle.TAXI.getDisplayName(), journeyResponse.getRoadVehicleName());
        assertEquals(4.00, journeyResponse.getRoadCost());
        assertNull(journeyResponse.getOutboundAirRoute());
    }

    @Test
    @DisplayName("Throws exception if request is missing passenger value")
    public void testFailsIfMissingPassengers() {
        Mockito.when(mockRequest.getPassengers()).thenReturn(null);
        Mockito.when(mockRequest.getHomeAirport()).thenReturn("B");
        Mockito.when(mockRequest.getHomeAirportDistanceMiles()).thenReturn(20);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new JourneyBuilder(mockRequest).withRoadJourney().build());
        assertEquals("Passengers must not be null", exception.getMessage());
    }

    @Test
    @DisplayName("Throws exception if request has passenger value less than 1")
    public void testFailsIfNonPositivePassengers() {
        Mockito.when(mockRequest.getPassengers()).thenReturn(0);
        Mockito.when(mockRequest.getHomeAirport()).thenReturn("B");
        Mockito.when(mockRequest.getHomeAirportDistanceMiles()).thenReturn(20);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new JourneyBuilder(mockRequest).withRoadJourney().build());
        assertEquals("Passengers must be greater than zero", exception.getMessage());
    }

    @Test
    @DisplayName("Throws exception if request is missing home airport value")
    public void testFailsIfMissingHomeAirport() {
        Mockito.when(mockRequest.getPassengers()).thenReturn(2);
        Mockito.when(mockRequest.getHomeAirport()).thenReturn(null);
        Mockito.when(mockRequest.getHomeAirportDistanceMiles()).thenReturn(20);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new JourneyBuilder(mockRequest).withRoadJourney().build());
        assertEquals("Home airport must be provided", exception.getMessage());
    }

    @Test
    @DisplayName("Calculates correct outbound air journey details from valid request")
    public void testCalculatesOutboundAirJourney() {
        Mockito.when(mockRequest.getPassengers()).thenReturn(2);
        Mockito.when(mockRequest.getHomeAirport()).thenReturn("B");
        Mockito.when(mockRequest.getDestinationAirport()).thenReturn("D");

        Map<String, List<Flight>> flightGraph = FlightGraphBuilder.buildGraph(SampleDataProvider.getFlights());
        JourneyResponse journeyResponse = new JourneyBuilder(mockRequest)
                .withAirJourney(flightGraph)
                .build();
        assertEquals("BF400--FD200", journeyResponse.getOutboundAirRoute());
        assertEquals( 120.00, journeyResponse.getOutboundAirCost());
    }

    @Test
    @DisplayName("Calculates correct inbound air journey details from valid request")
    public void testCalculatesInboundAirJourney() {
        Mockito.when(mockRequest.getPassengers()).thenReturn(2);
        Mockito.when(mockRequest.getHomeAirport()).thenReturn("B");
        Mockito.when(mockRequest.getDestinationAirport()).thenReturn("D");

        Map<String, List<Flight>> flightGraph = FlightGraphBuilder.buildGraph(SampleDataProvider.getFlights());
        JourneyResponse journeyResponse = new JourneyBuilder(mockRequest)
                .withAirJourney(flightGraph)
                .build();
        assertEquals("DE300--EB500", journeyResponse.getInboundAirRoute());
        assertEquals( 160.00, journeyResponse.getInboundAirCost());
    }

    @Test
    @DisplayName("Calculates full travel itinerary with valid input")
    public void testFullTravelItinerary() {
        Mockito.when(mockRequest.getPassengers()).thenReturn(2);
        Mockito.when(mockRequest.getHomeAirport()).thenReturn("B");
        Mockito.when(mockRequest.getHomeAirportDistanceMiles()).thenReturn(20);
        Mockito.when(mockRequest.getDestinationAirport()).thenReturn("D");

        Map<String, List<Flight>> flightGraph = FlightGraphBuilder.buildGraph(SampleDataProvider.getFlights());
        JourneyResponse journeyResponse = new JourneyBuilder(mockRequest)
                .withRoadJourney()
                .withAirJourney(flightGraph)
                .build();
        assertEquals(RoadVehicle.CAR.getDisplayName(), journeyResponse.getRoadVehicleName());
        assertEquals(11.00, journeyResponse.getRoadCost());
        assertEquals("BF400--FD200", journeyResponse.getOutboundAirRoute());
        assertEquals( 120.00, journeyResponse.getOutboundAirCost());
        assertEquals("DE300--EB500", journeyResponse.getInboundAirRoute());
        assertEquals( 160.00, journeyResponse.getInboundAirCost());
        assertEquals(291.00, journeyResponse.getTotalCost());
    }

    @Test
    @DisplayName("Calculates full travel itinerary when no air route exists")
    public void testFullTravelItineraryWithMissingFlight() {
        Mockito.when(mockRequest.getPassengers()).thenReturn(2);
        Mockito.when(mockRequest.getHomeAirport()).thenReturn("A");
        Mockito.when(mockRequest.getHomeAirportDistanceMiles()).thenReturn(20);
        Mockito.when(mockRequest.getDestinationAirport()).thenReturn("D");

        Map<String, List<Flight>> flightGraph = FlightGraphBuilder.buildGraph(SampleDataProvider.getFlights());
        JourneyResponse journeyResponse = new JourneyBuilder(mockRequest)
                .withRoadJourney()
                .withAirJourney(flightGraph)
                .build();
        assertEquals(RoadVehicle.CAR.getDisplayName(), journeyResponse.getRoadVehicleName());
        assertEquals(11.00, journeyResponse.getRoadCost());
        assertEquals("AB800--BF400--FD200", journeyResponse.getOutboundAirRoute());
        assertEquals( 280.00, journeyResponse.getOutboundAirCost());
        assertEquals("No inbound flight", journeyResponse.getInboundAirRoute());
        assertEquals( 0, journeyResponse.getInboundAirCost());
        assertEquals(0, journeyResponse.getTotalCost());
    }
}
