//Need time to complete

//import org.apache.commons.io.output.TeeOutputStream;
//import org.example.Printer;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.BlockingDeque;
//import java.util.concurrent.LinkedBlockingDeque;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.assertArrayEquals;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class PrinterBUnitTest {
//
//    private BlockingDeque<Integer> stack;
//    private ByteArrayOutputStream outputStream;
//    private Printer printer;
//
//    @BeforeEach
//    void setUp() {
//        stack = new LinkedBlockingDeque<>(List.of(1, 2));
//        outputStream = new ByteArrayOutputStream();
//        printer = new Printer(stack);
//    }
//
//    @Test
//    void run() throws InterruptedException, IOException {
//        try (PrintStream originalOut = System.out;
//             TeeOutputStream teeOutputStream = new TeeOutputStream(originalOut, outputStream);
//             PrintStream capturedOut = new PrintStream(teeOutputStream)) {
//
//            // Create a new PrintStream to capture and print to the console
//            System.setOut(capturedOut);
//
//            printer.start();
//            Thread.sleep((long) Printer.PRINTER_TIME_MAX * stack.size() + 1000);
//            printer.interrupt();
//            printer.join(); // Wait for the printer thread to finish
//
//            // Extract the printed values from the captured output
//            String printedOutput = outputStream.toString().trim();
////            System.out.println("1");
//            System.out.println(printedOutput);
//            String[] printedValues = printedOutput.split(System.lineSeparator());
////            System.out.println("2");
//            System.out.println(Arrays.toString(printedValues));
//
//            // Create an array of expected values
//            String[] expectedValues = [2, 1];
//            System.out.println("Before assertion");
//
//            String[] trimmedPrintedValues = Arrays.stream(printedValues)
//                    .map(String::trim)
//                    .toArray(String[]::new);
//
//            assertArrayEquals(expectedValues, trimmedPrintedValues);
//
////            // Convert the printed values to an Integer list
////            List<Integer> printedValuesList = Arrays.stream(printedValues)
////                    .map(Integer::parseInt)
////                    .collect(Collectors.toList());
////            System.out.println("4");
//
//            // Assert that the printed values match the expected values
//
//
//        }
//    }
//}
