import Journey.JourneyBuilder;
import Journey.JourneyRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class JourneyBuilderTest {
    private JourneyRequest mockRequest;

    @Test
    @DisplayName("Can't be instantiated with null JourneyRequest")
    public void testUnsuccessfulNullRequestInstantiation() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new JourneyBuilder(null);
        });
        assertEquals("Journey Request cannot be null", exception.getMessage());
    }
}
