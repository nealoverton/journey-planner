import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExampleTest {
    ExampleClass exampleClass;

    @BeforeEach
    public void setUp() {
        exampleClass = new ExampleClass();
    }

    @Test
    @DisplayName("Example should work")
    public void testExample() {
        assertEquals(5, exampleClass.giveMeFive());
    }
}
