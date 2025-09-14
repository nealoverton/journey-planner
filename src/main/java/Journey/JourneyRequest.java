package Journey;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JourneyRequest {
    private final Integer passengers;
    private final Integer homeAirportDistanceMiles;
    private final String homeAirport;
    private final String destinationAirport;

    private JourneyRequest(Integer passengers, Integer homeAirportDistanceMiles, String homeAirport, String destinationAirport) {
        this.passengers = passengers;
        this.homeAirportDistanceMiles = homeAirportDistanceMiles;
        this.homeAirport = homeAirport;
        this.destinationAirport = destinationAirport;
    }

    public static Optional<JourneyRequest> fromInput(Integer passengers, String homeAirportJourney, String destinationAirport) {
        if (passengers == null
                || passengers <= 0
                || StringUtils.isBlank(homeAirportJourney)
                || StringUtils.isBlank(destinationAirport)
        ) {
            return Optional.empty();
        }

        Pattern homeAirportJourneyPattern = Pattern.compile("^([A-Z]+)(\\d+)$");
        Matcher homeAirportJourneyMatcher = homeAirportJourneyPattern.matcher(homeAirportJourney);
        if (homeAirportJourneyMatcher.matches()) {
            try {
                String homeAirport = homeAirportJourneyMatcher.group(1);
                Integer homeAirportDistanceMiles = Integer.parseInt(homeAirportJourneyMatcher.group(2));
                return Optional.of(new JourneyRequest(passengers, homeAirportDistanceMiles, homeAirport, destinationAirport));
            } catch (NumberFormatException exception) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public Integer getPassengers() {
        return passengers;
    }

    public Integer getHomeAirportDistanceMiles() {
        return homeAirportDistanceMiles;
    }

    public String getHomeAirport() {
        return homeAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }
}
