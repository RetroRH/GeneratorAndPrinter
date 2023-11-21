package org.example;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {
    public static void main(String[] args) {

        // Deque data structure where Generators put the generated values and from where Printer takes values to output
        BlockingDeque<Integer> sharedStack = new LinkedBlockingDeque<>(50);

        // Generators and Printer threads are instantiated here
        NumberGenerator progressionProducer_A = new NumberGenerator(sharedStack, 3, 3);
        NumberGenerator progressionProducer_B = new NumberGenerator(sharedStack, 5, 5);
        Printer printer = new Printer(sharedStack);

        // Generators and Printer threads starts here
        progressionProducer_A.start();
        progressionProducer_B.start();
        printer.start();
    }
}