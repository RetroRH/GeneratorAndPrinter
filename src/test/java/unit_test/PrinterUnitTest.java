package unit_test;

import org.example.Printer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PrinterUnitTest {

    private BlockingDeque<Integer> stack;
    private Printer printer;

    @BeforeEach
    void setUp() {
        stack = new LinkedBlockingDeque<>(List.of(1, 2, 3, 4, 5));
        printer = new Printer(stack);
    }

    @Test
    void run() throws InterruptedException {

        printer.start();
        Thread.sleep((long) Printer.PRINTER_TIME_MAX * stack.size() + 1000);
        printer.interrupt();
        printer.join();

        assertTrue(stack.isEmpty());
    }
}