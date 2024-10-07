package ait.numbers.model;

import ait.numbers.task.OneGroupSum;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorGroupSum extends GroupSum{
    public ExecutorGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() throws InterruptedException {
        OneGroupSum[] tasks = new OneGroupSum[numberGroups.length];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new OneGroupSum(numberGroups[i]);
        }

        int poolsize = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(poolsize);

        for (int i = 0; i < tasks.length; i++) {
            executorService.execute(tasks[i]);
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // TODO use ExecutorService
        return Arrays.stream(tasks).mapToInt(OneGroupSum::getSum).sum();
    }
}
