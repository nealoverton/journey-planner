import com.example.Constant;
import com.example.flight.Flight;
import com.example.flight.FlightGraphBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FlightGraphBuilderTest {
    @Test
    @DisplayName("Create graph from valid data")
    public void testCreateGraph() {
        Map<String, List<Flight>> graph = FlightGraphBuilder.buildGraph(Constant.FLIGHTS);
        assertEquals(6, graph.size());
        assertEquals(3, graph.get("C").size());
        assertEquals(1, graph.get("A").size());
        assertNull(graph.get("G"));

        boolean hasCD400 = graph.get("C").stream()
                .anyMatch(flight -> flight.getTargetAirport().equals("D") && flight.getDistanceInMiles() == 400);
        boolean hasEB500 = graph.get("E").stream()
                .anyMatch(flight -> flight.getTargetAirport().equals("B") && flight.getDistanceInMiles() == 500);
        boolean hasBC900 = graph.get("B").stream()
                .anyMatch(flight -> flight.getTargetAirport().equals("C") && flight.getDistanceInMiles() == 900);

        assertTrue(hasCD400, "Should contain C -> D with distance 400");
        assertTrue(hasEB500, "Should contain E -> B with distance 500");
        assertTrue(hasBC900, "Should contain B -> C with distance 900");
    }

    @Test
    @DisplayName("Skip invalid data when creating graph")
    public void testSkipInvalidData() {
        Map<String, List<Flight>> graph = FlightGraphBuilder.buildGraph(List.of("AB200", "ZZZZZZZZZZZZ"));
        assertEquals(1, graph.size());

        boolean hasAB200 = graph.get("A").stream()
                .anyMatch(flight -> flight.getTargetAirport().equals("B") && flight.getDistanceInMiles() == 200);

        assertTrue(hasAB200, "Should contain A -> B with distance 200");
        assertNull(graph.get("Z"));
    }
}
