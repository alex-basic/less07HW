package ait.numbers.model;

import ait.numbers.task.OneGroupSum;
import ait.utils.pool.ThreadPool;

import java.util.Arrays;

public class CustomThreadPoolGroupSum extends GroupSum{
    public CustomThreadPoolGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() throws InterruptedException {
        // TODO: Use ait.utils.pool.ThreadPool
        OneGroupSum[] tasks = new OneGroupSum[numberGroups.length];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new OneGroupSum(numberGroups[i]);
        }

        ThreadPool threadPool = new ThreadPool(tasks.length);

        for (int i = 0; i < tasks.length; i++) {
            threadPool.execute(tasks[i]);
        }

        threadPool.shutDown();
        threadPool.joinToPool(1);


        return Arrays.stream(tasks).mapToInt(OneGroupSum::getSum).sum();
    }
}
