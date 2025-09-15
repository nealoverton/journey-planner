package com.example.integration;

import com.example.Constant;
import com.example.SampleDataProvider;
import com.example.flight.FlightGraphBuilder;
import com.example.journey.JourneyInput;
import com.example.journey.JourneyRequest;
import com.example.journey.JourneyResponse;
import com.example.journey.JourneyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;


public class SampleDataProof {
    private String padRight(String text, int columnWidth) {
        if (text.length() >= columnWidth) return text;
        return text + " ".repeat(columnWidth - text.length());
    }

    @Test
    @DisplayName("Show output from processing all sample data")
    public void testSampleDataOutput() {
        List<JourneyInput> journeyInputs = SampleDataProvider.getJourneyInputs();

        StringBuilder outputBuilder = new StringBuilder()
                .append("SAMPLE INPUT")
                .append(Constant.NEW_LINE)
                .append(Constant.NEW_LINE)
                .append("| #  | passengers | home to airport | destination |")
                .append(Constant.NEW_LINE)
                .append("|----|------------|-----------------|-------------|")
                .append(Constant.NEW_LINE);

        int index = 1;
        for (JourneyInput input : journeyInputs) {
            outputBuilder
                    .append(Constant.VERTICAL_BAR)
                    .append(padRight(Constant.SPACE + (index++), 4))
                    .append(Constant.VERTICAL_BAR)
                    .append(padRight(Constant.SPACE + input.getPassengers(), 12))
                    .append(Constant.VERTICAL_BAR)
                    .append(padRight(Constant.SPACE + input.getHomeAirportJourney(), 17))
                    .append(Constant.VERTICAL_BAR)
                    .append(padRight(Constant.SPACE + input.getDestinationAirport(), 13))
                    .append(Constant.VERTICAL_BAR)
                    .append(Constant.NEW_LINE);
        }

        JourneyService journeyService = new JourneyService(FlightGraphBuilder.buildGraph(SampleDataProvider.getFlights()));
        List<JourneyRequest> journeyRequests = SampleDataProvider.getJourneyRequests();
        List<JourneyResponse> journeyResponses = journeyService.processJourneyList(journeyRequests);

        outputBuilder
                .append(Constant.NEW_LINE)
                .append(Constant.NEW_LINE)
                .append("SAMPLE OUTPUT")
                .append(Constant.NEW_LINE)
                .append(Constant.NEW_LINE)
                .append("| #  | vehicle | vehicle return cost | outbound route         | outbound cost | inbound route        | inbound cost | total cost |")
                .append(Constant.NEW_LINE)
                .append("|----|---------|---------------------|------------------------|---------------|----------------------|--------------|------------|")
                .append(Constant.NEW_LINE);

        index = 1;
        for (JourneyResponse response : journeyResponses) {
            outputBuilder
                    .append(Constant.VERTICAL_BAR)
                    .append(padRight(Constant.SPACE + (index++), 4))
                    .append(Constant.VERTICAL_BAR)
                    .append(padRight(Constant.SPACE + response.getRoadVehicleName(), 9))
                    .append(Constant.VERTICAL_BAR)
                    .append(padRight(Constant.SPACE + response.getRoadCost(), 21))
                    .append(Constant.VERTICAL_BAR)
                    .append(padRight(Constant.SPACE + response.getOutboundAirRoute(), 24))
                    .append(Constant.VERTICAL_BAR)
                    .append(padRight(Constant.SPACE + response.getOutboundAirCost(), 15))
                    .append(Constant.VERTICAL_BAR)
                    .append(padRight(Constant.SPACE + response.getInboundAirRoute(), 22))
                    .append(Constant.VERTICAL_BAR)
                    .append(padRight(Constant.SPACE + response.getInboundAirCost(), 14))
                    .append(Constant.VERTICAL_BAR)
                    .append(padRight(Constant.SPACE + response.getTotalCost(), 12))
                    .append(Constant.VERTICAL_BAR)
                    .append(Constant.NEW_LINE);
        }

        System.out.println(outputBuilder.toString());
    }
}
