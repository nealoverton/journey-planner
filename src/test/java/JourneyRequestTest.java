import Journey.JourneyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class JourneyRequestTest {
    @Test
    @DisplayName("Can be instantiated with valid input")
    public void testSuccessfulInstantiation() {
        Optional<JourneyRequest> requestJourneyOptional = JourneyRequest.fromInput(2, "B20", "D");
        assertTrue(requestJourneyOptional.isPresent());

        JourneyRequest journeyRequest = requestJourneyOptional.get();
        assertEquals(2, journeyRequest.getPassengers());
        assertEquals("B", journeyRequest.getHomeAirport());
        assertEquals(20, journeyRequest.getHomeAirportDistanceMiles());
    }

    @Test
    @DisplayName("Fails to instantiate with invalid homeAirportJourney input")
    public void testUnsuccessfulInstantiation() {
        Optional<JourneyRequest> requestJourneyOptional = JourneyRequest.fromInput(2, "B20dfgds", "D");
        assertTrue(requestJourneyOptional.isEmpty());
    }
}
