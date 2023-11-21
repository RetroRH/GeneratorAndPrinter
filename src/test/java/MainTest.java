import org.example.NumberGenerator;
import org.example.Printer;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    private final static int printerTimeMax = Printer.PRINTER_TIME_MAX;

    @Test
    void testProgram() throws InterruptedException {
        BlockingDeque<Integer> sharedStack = new LinkedBlockingDeque<>(50);

        // Create instances of the classes
        NumberGenerator progressionProducer_A = new NumberGenerator(sharedStack, 3, 3);
        NumberGenerator progressionProducer_B = new NumberGenerator(sharedStack, 5, 5);
        Printer printer = new Printer(sharedStack);

        // Start threads
        progressionProducer_A.start();
        progressionProducer_B.start();
        printer.start();

        // Let the threads run for some time
        Thread.sleep(5000);

        // Interrupt the threads
        progressionProducer_A.interrupt();
        progressionProducer_B.interrupt();


        // Join the threads to ensure they have completed
        progressionProducer_A.join();
        progressionProducer_B.join();

        // Wait for printer to printout and clean all gathered values in deque
        int requiredCleanTime = sharedStack.size() * printerTimeMax + 1000;
        Thread.sleep(requiredCleanTime);
        printer.interrupt();
        printer.join();

        // Assure that stack is empty after printing all values
        assertTrue(sharedStack.isEmpty());
    }
}


