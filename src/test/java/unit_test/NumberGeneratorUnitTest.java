package unit_test;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Stream;

import org.example.NumberGenerator;
//import org.junit.Before;
//import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

//import static org.junit.Assert.*;

public class NumberGeneratorUnitTest {

    private static BlockingDeque<Integer> sharedStack;
    private static NumberGenerator numberGenerator;
    private static boolean isParameterizedTest = false;


    @BeforeAll
    public static void setUpBeforeAll() {

        if (isParameterizedTest) {
            sharedStack = new LinkedBlockingDeque<>(50);
            numberGenerator = new NumberGenerator(sharedStack, 3, 3);
        }
    }

    @BeforeEach
    public void setUp() {

        if (!isParameterizedTest) {
            sharedStack = new LinkedBlockingDeque<>(50);
            numberGenerator = new NumberGenerator(sharedStack, 3, 3);
        }
    }

    @Test
    public void testGetNotNull() {

        assertNotNull(numberGenerator.get());
    }


    @ParameterizedTest
    @MethodSource("expectedValuesProvider")
    public void testGetCheckValueWithParameter(int expectedValue) {

        isParameterizedTest = true;
        int result = numberGenerator.get();
        assertEquals(expectedValue, result);
    }

    private static Stream<Integer> expectedValuesProvider() {
        // Provide the expected values in the sequence
        return Stream.of(3, 6, 9, 12, 15, 18, 21, 24, 27, 30);
    }



    @Test
    public void testRun() throws InterruptedException {

        numberGenerator.start();

        Thread.sleep(NumberGenerator.GENERATOR_TIME_MAX * 5);
        numberGenerator.interrupt();

        while (!sharedStack.isEmpty()) {
            Integer poppedElement = sharedStack.pop();
            assert poppedElement % numberGenerator.getStep() == 0 : "Assertion failed: One or more elements are not divisible by %s".formatted(numberGenerator.getStep());
        }
    }
}

