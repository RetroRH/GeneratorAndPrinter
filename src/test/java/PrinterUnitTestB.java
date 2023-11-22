import org.example.Printer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrinterUnitTestB {

    private BlockingDeque<Integer> stack;
    private ByteArrayOutputStream outputStream;
    private Printer printer;

    @BeforeEach
    void setUp() {
        stack = new LinkedBlockingDeque<>(List.of(1, 2, 3, 4, 5));
        outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        printer = new Printer(stack);
    }

    @Test
    @Test
    void run() throws InterruptedException {
        try (PrintStream originalOut = System.out;
             PrintStream capturedOut = new PrintStream(outputStream)) {

            // Create a new PrintStream to capture and print to the console
            PrintStream combinedOut = new PrintStream(new TeeOutputStream(originalOut, capturedOut));
            System.setOut(combinedOut);

            printer.start();
            Thread.sleep((long) Printer.PRINTER_TIME_MAX * stack.size() + 1000);
            printer.interrupt();
            printer.join(); // Wait for the printer thread to finish

            // Extract the printed values from the captured output
            String printedOutput = outputStream.toString().trim();
            String[] printedValues = printedOutput.split(System.lineSeparator());

            // Create an array of expected values
            Integer[] expectedValues = {5, 4, 3, 2, 1};

            // Convert the printed values to an Integer list
            List<Integer> printedValuesList = Arrays.stream(printedValues)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            // Assert that the printed values match the expected values
            assertEquals(Arrays.asList(expectedValues), printedValuesList);
        } // System.out is automatically restored here
    }


}

