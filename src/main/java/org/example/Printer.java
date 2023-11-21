package org.example;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ThreadLocalRandom;

class Printer extends Thread {

    private final BlockingDeque<Integer> sharedStack;
    private final static int PRINTER_TIME_MIN = 2000;
    final static int PRINTER_TIME_MAX = 3000;

    public Printer(BlockingDeque<Integer> sharedStack) {

        this.sharedStack = sharedStack;
    }

    @Override
    public void run() {

        while (true) {

            try {

                Thread.sleep(ThreadLocalRandom.current().nextInt(PRINTER_TIME_MIN, PRINTER_TIME_MAX));
                // Pop the top element from the stack in LIFO order and print it
                Integer numberToPrint = sharedStack.pollLast();
                if (numberToPrint != null) {
                    System.out.println("Printer printed: " + numberToPrint);
                }
            } catch (InterruptedException e) {

                System.out.println("Printer thread was interrupted");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
