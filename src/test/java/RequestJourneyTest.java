import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class RequestJourneyTest {
    @Test
    @DisplayName("Can be instantiated with valid input")
    public void testSuccessfulInstantiation() {
        Optional<RequestJourney> requestJourneyOptional = RequestJourney.fromInput(2, "B20", "D");
        assertTrue(requestJourneyOptional.isPresent());

        RequestJourney requestJourney = requestJourneyOptional.get();
        assertEquals(2, requestJourney.getPassengers());
        assertEquals("B", requestJourney.getHomeAirport());
        assertEquals(20, requestJourney.getHomeAirportDistanceMiles());
    }

    @Test
    @DisplayName("Fails to instantiate with invalid homeAirportJourney input")
    public void testUnsuccessfulInstantiation() {
        Optional<RequestJourney> requestJourneyOptional = RequestJourney.fromInput(2, "B20dfgds", "D");
        assertTrue(requestJourneyOptional.isEmpty());
    }
}
