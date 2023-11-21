package integration_test;

import org.example.NumberGenerator;
import org.example.Printer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IntegrationTest {

    private static BlockingDeque<Integer> sharedStack;
    private static NumberGenerator generator_A;
    private static NumberGenerator generator_B;
    private static Printer printer;

    @BeforeAll
    static void setUp() {

        // Create data structure to put values
        sharedStack = new LinkedBlockingDeque<>(50);

        // Create instances of the threads
        generator_A = new NumberGenerator(sharedStack, 3, 3);
        generator_B = new NumberGenerator(sharedStack, 5, 5);
        printer = new Printer(sharedStack);
    }

    @Test
    @Order(1)
    void testGeneratorsFillStack() throws InterruptedException {

        // Start generator threads
        generator_A.start();
        generator_B.start();

        // Stop the main thread and allow generators to fill sharedStack with some numbers
        Thread.sleep(NumberGenerator.GENERATOR_TIME_MAX * 3);

        // Interrupt the threads
        generator_A.interrupt();
        generator_B.interrupt();

        // Join the generator threads to ensure they have completed
        generator_A.join();
        generator_B.join();

        // Asserting that the sharedStack was filled by generators as expected. This proves that generators are in working state
        assertFalse(sharedStack.isEmpty());
    }

    @Test
    @Order(2)
    void testPrinterEmptiesStack() throws InterruptedException {

        // Wait for printer to print out and clean all the gathered values in the sharedStack
        printer.start();

        // Maximum amount of time with 1 second margin to clean up the sharedStack for Printer thread
        int requiredCleanTime = sharedStack.size() * Printer.PRINTER_TIME_MAX + 1000;

        // Stop the main thread for the amount of time that the Printer thread needs to clean up the sharedStack
        Thread.sleep(requiredCleanTime);

        // Stop the Printer thread
        printer.interrupt();
        printer.join();

        // Assure that stack is empty after printing all values. This proves that Printer is in working state
        assertTrue(sharedStack.isEmpty());
    }
}



