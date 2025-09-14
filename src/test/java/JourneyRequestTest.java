import Journey.JourneyBuilder;
import Journey.JourneyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;
import java.util.stream.Stream;

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

    @DisplayName("Fails to instantiate with invalid homeAirportJourney input")
    @ParameterizedTest(name = "Test invalid homeAirportJourney input: {0}")
    @ValueSource(strings = {"B20dfgds", "9B20", "a10", "100", "aaa", "", "B-20"})
    public void testUnsuccessfulInstantiationWithInvalidHomeAirportJourney(String invalidInput) {
        Optional<JourneyRequest> requestJourneyOptional = JourneyRequest.fromInput(2, invalidInput, "D");
        assertTrue(requestJourneyOptional.isEmpty());
    }

    @Test
    @DisplayName("Fails to instantiate with empty destinationAirport input")
    public void testUnsuccessfulInstantiationWithEmptyDestinationAirport() {
        Optional<JourneyRequest> requestJourneyOptional = JourneyRequest.fromInput(2, "B20", "");
        assertTrue(requestJourneyOptional.isEmpty());
    }

    @DisplayName("Fails to instantiate with non-positive passengers input")
    @ParameterizedTest(name = "Test invalid passengers input: {0}")
    @ValueSource(ints = {0, -1})
    public void testUnsuccessfulInstantiationWithInvalidPassengers(Integer invalidInput) {
        Optional<JourneyRequest> requestJourneyOptional = JourneyRequest.fromInput(invalidInput, "B20", "D");
        assertTrue(requestJourneyOptional.isEmpty());
    }

    @DisplayName("Fails to instantiate with null input")
    @ParameterizedTest(name = "Test null arguments: passengers={0}, homeAirportJourney={1}, destinationAirport={2}")
    @MethodSource("nullArgumentsProvider")
    public void testUnsuccessfulInstantiationWithNullValues(Integer passengers, String homeAirportJourney, String destinationAirport) {
        Optional<JourneyRequest> requestJourneyOptional = JourneyRequest.fromInput(passengers, homeAirportJourney, destinationAirport);
        assertTrue(requestJourneyOptional.isEmpty());
    }

    private static Stream<Arguments> nullArgumentsProvider() {
        return Stream.of(
                Arguments.of(null, "B20", "D"),
                Arguments.of(2, null, "D"),
                Arguments.of(2, "B20", null)
        );
    }
}
