package org.example;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class NumberGenerator extends Thread implements Supplier<Integer> {

    private int currentValue;
    private final int step;
    private final BlockingDeque<Integer> sharedStack;
    private final static int GENERATOR_TIME_MIN = 2000;
    public final static int GENERATOR_TIME_MAX = 2500;

    // Constructor to initialize the starting term and common difference
    public NumberGenerator(BlockingDeque<Integer> sharedStack, int initialValue, int step) {

        this.sharedStack = sharedStack;
        this.currentValue = initialValue;
        this.step = step;
    }

    @Override
    public Integer get() {

        int nextTerm = currentValue;
        currentValue += step;
        return nextTerm;
    }

    @Override
    public void run() {

        while (true) {

            int term = get();
            try {
                sharedStack.put(term);
                System.out.println("Thread " + Thread.currentThread().getId() + ": " + term);
                System.out.println("Shared Queue now contains: " + sharedStack);
                // Generator generates numbers within random time intervals between TIME_1 and TIME_2
                Thread.sleep(ThreadLocalRandom.current().nextInt(GENERATOR_TIME_MIN, GENERATOR_TIME_MAX));
            } catch (InterruptedException e) {
                System.out.printf("Thread %s was interrupted%n", Thread.currentThread().getName());
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public int getStep() {
        return this.step;
    }
}


