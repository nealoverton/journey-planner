package com.example.integration;

import com.example.SampleDataProvider;
import com.example.flight.FlightGraphBuilder;
import com.example.journey.JourneyRequest;
import com.example.journey.JourneyResponse;
import com.example.journey.JourneyService;
import com.example.journey.RoadVehicle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JourneyServiceTest {
    private final JourneyService journeyService = new JourneyService(FlightGraphBuilder.buildGraph(SampleDataProvider.getFlights()));
    private final List<JourneyRequest> journeyRequests = SampleDataProvider.getJourneyRequests();

    @Test
    @DisplayName("Process single journey request with processJourney")
    public void testProcessJourney() {
        JourneyResponse journeyResponse = journeyService.processJourney(journeyRequests.get(0));
        assertEquals(RoadVehicle.CAR.getDisplayName(), journeyResponse.getRoadVehicleName());
        assertEquals(11.00, journeyResponse.getRoadCost());
        assertEquals("BF400--FD200", journeyResponse.getOutboundAirRoute());
        assertEquals(120.00, journeyResponse.getOutboundAirCost());
        assertEquals("DE300--EB500", journeyResponse.getInboundAirRoute());
        assertEquals(160.00, journeyResponse.getInboundAirCost());
        assertEquals(291.00, journeyResponse.getTotalCost());
    }

    @Test
    @DisplayName("Process multiple journey requests with processJourneyList")
    public void testProcessJourneyList() {
        List<JourneyResponse> journeyResponses = journeyService.processJourneyList(journeyRequests);
        assertEquals(journeyRequests.size(), journeyResponses.size());

        JourneyResponse firstResponse = journeyResponses.get(0);
        assertEquals(RoadVehicle.CAR.getDisplayName(), firstResponse.getRoadVehicleName());
        assertEquals(11.00, firstResponse.getRoadCost());
        assertEquals("BF400--FD200", firstResponse.getOutboundAirRoute());
        assertEquals(120.00, firstResponse.getOutboundAirCost());
        assertEquals("DE300--EB500", firstResponse.getInboundAirRoute());
        assertEquals(160.00, firstResponse.getInboundAirCost());
        assertEquals(291.00, firstResponse.getTotalCost());

        JourneyResponse sixthResponse = journeyResponses.get(5);
        assertEquals(RoadVehicle.CAR.getDisplayName(), sixthResponse.getRoadVehicleName());
        assertEquals(14.00, sixthResponse.getRoadCost());
        assertEquals("BC900", sixthResponse.getOutboundAirRoute());
        assertEquals(450.00, sixthResponse.getOutboundAirCost());
        assertEquals("CE200--EB500", sixthResponse.getInboundAirRoute());
        assertEquals(350.00, sixthResponse.getInboundAirCost());
        assertEquals(814.00, sixthResponse.getTotalCost());
    }
}
