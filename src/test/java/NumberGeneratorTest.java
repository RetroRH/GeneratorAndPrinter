import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.example.NumberGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

public class NumberGeneratorTest {

    private BlockingDeque<Integer> sharedStack;
    private NumberGenerator numberGenerator;

    @Before
    public void setUp() {

        sharedStack = new LinkedBlockingDeque<>(50);
        numberGenerator = new NumberGenerator(sharedStack, 3, 3);
    }

    @Test
    public void testGet() {
        assertNotNull(numberGenerator.get());
    }


    @Test
    public void testGetResults() {

        // Set up the expected values for the initial and subsequent calls to get()
        int expectedInitialValue = 3;
        int expectedNextValue = expectedInitialValue + numberGenerator.getStep();

        // Test the initial call to get()
        int initialValue = numberGenerator.get();
        assertEquals("Initial value should be as expected", expectedInitialValue, initialValue);


//         Test the subsequent call to get()
        int nextValue = numberGenerator.get();
        assertEquals("Next value should be as expected", expectedNextValue, nextValue);
    }




    @Test
    public void testRun() throws InterruptedException {

        numberGenerator.start();

        Thread.sleep(10000);
        numberGenerator.interrupt();

        while (!sharedStack.isEmpty()) {
            Integer poppedElement = sharedStack.pop();
            assert poppedElement % numberGenerator.getStep() == 0 : "Assertion failed: condition not met";
        }
    }
}
