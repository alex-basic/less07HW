package ait.numbers.model;

import ait.numbers.task.OneGroupSum;

import java.util.Arrays;

public class ParallelStreamGroupSum extends GroupSum {
    public ParallelStreamGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() throws InterruptedException {
        // TODO use parallel stream
        OneGroupSum[] tasks = new OneGroupSum[numberGroups.length];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new OneGroupSum(numberGroups[i]);
        }

        Thread[] threads = new Thread[tasks.length];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }

        return Arrays.stream(tasks).parallel().mapToInt(OneGroupSum::getSum).sum();
    }
}
