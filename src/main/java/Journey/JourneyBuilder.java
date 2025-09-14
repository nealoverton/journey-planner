package Journey;

public class JourneyBuilder {
    private JourneyRequest journeyRequest;
    private boolean includeRoadJourney;
    private boolean includeAirJourney;

    public JourneyBuilder(JourneyRequest journeyRequest) {
        if (journeyRequest == null) {
            throw new IllegalArgumentException("Journey Request cannot be null");
        }
        this.journeyRequest = journeyRequest;
    }

    public JourneyBuilder withRoadJourney() {
        includeRoadJourney = true;
        return this;
    }

    public JourneyBuilder withAirJourney() {
        includeAirJourney = true;
        return this;
    }

    public JourneyResponse build() {
        return new JourneyResponse();
    }

    private void validateRequest() {

    }
}
