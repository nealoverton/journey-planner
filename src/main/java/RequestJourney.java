import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestJourney {
    private int passengers;
    private int homeAirportDistanceMiles;
    private String homeAirport;
    private String destinationAirport;

    private RequestJourney(int passengers, int homeAirportDistanceMiles, String homeAirport, String destinationAirport) {
        this.passengers = passengers;
        this.homeAirportDistanceMiles = homeAirportDistanceMiles;
        this.homeAirport = homeAirport;
        this.destinationAirport = destinationAirport;
    }

    public static Optional<RequestJourney> fromInput(int passengers, String homeAirportJourney, String destinationAirport) {
        Pattern homeAirportJourneyPattern = Pattern.compile("^([A-Z]+)(\\d+)$");
        Matcher homeAirportJourneyMatcher = homeAirportJourneyPattern.matcher(homeAirportJourney);
        if (homeAirportJourneyMatcher.matches()) {
            try {
                String homeAirport = homeAirportJourneyMatcher.group(1);
                int homeAirportDistanceMiles = Integer.parseInt(homeAirportJourneyMatcher.group(2));
                return Optional.of(new RequestJourney(passengers, homeAirportDistanceMiles, homeAirport, destinationAirport));
            } catch (NumberFormatException exception) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public int getHomeAirportDistanceMiles() {
        return homeAirportDistanceMiles;
    }

    public void setHomeAirportDistanceMiles(int homeAirportDistanceMiles) {
        this.homeAirportDistanceMiles = homeAirportDistanceMiles;
    }

    public String getHomeAirport() {
        return homeAirport;
    }

    public void setHomeAirport(String homeAirport) {
        this.homeAirport = homeAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }
}
