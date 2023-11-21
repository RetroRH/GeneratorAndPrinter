import org.example.NumberGenerator;
import org.example.Printer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import static org.junit.jupiter.api.Assertions.*;

class PrinterTest {

    private BlockingDeque<Integer> sharedStack;
    private NumberGenerator numberGenerator;
    private Printer printer;

    @BeforeEach
    void setUp() {
        sharedStack = new LinkedBlockingDeque<>(50);
        numberGenerator = new NumberGenerator(sharedStack, 5, 5);
        printer = new Printer(sharedStack);
    }

    @Test
    void run() throws InterruptedException {
        numberGenerator.start();

        Thread.sleep(NumberGenerator.GENERATOR_TIME_MAX * 5);
        numberGenerator.interrupt();

        printer.start();
        Thread.sleep((long) Printer.PRINTER_TIME_MAX * sharedStack.size() + 1000);
        printer.interrupt();

        assert sharedStack.isEmpty() : "Assertion failed: sharedStack is not empty";
    }
}