//This Class under modification


//package unit_test;
//
//import java.util.concurrent.BlockingDeque;
//import java.util.concurrent.LinkedBlockingDeque;
//
//import org.example.NumberGenerator;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class NumberGeneratorUnitTest {
//
//    private BlockingDeque<Integer> sharedStack;
//    private NumberGenerator numberGenerator;
//
//    @Before
//    public void setUp() {
//
//        sharedStack = new LinkedBlockingDeque<>(50);
//        numberGenerator = new NumberGenerator(sharedStack, 3, 3);
//    }
//
//    @Test
//    public void testGetNotNull() {
//        assertNotNull(numberGenerator.get());
//    }
//
//    @Test
//    public void testGetCheckValue() {
//        int result = numberGenerator.get();
//        assertEquals(3, result);
//    }
//
//
//
//
//    @Test
//    public void testRun() throws InterruptedException {
//
//        numberGenerator.start();
//
//        Thread.sleep(10000);
//        numberGenerator.interrupt();
//
//        while (!sharedStack.isEmpty()) {
//            Integer poppedElement = sharedStack.pop();
//            assert poppedElement % numberGenerator.getStep() == 0 : "Assertion failed: condition not met";
//        }
//    }
//}
//
