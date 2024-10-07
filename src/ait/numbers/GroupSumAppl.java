package ait.numbers;

import ait.numbers.model.*;
import ait.numbers.test.GroupSumPerfomanceTest;

import java.util.Random;

public class GroupSumAppl {
    private static final int N_GROUPS = 10000;
    private static final int NUMERS_PER_GROUP = 10000;
    private static final int[][] numbers = new int[N_GROUPS][NUMERS_PER_GROUP];
    private static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        fillArray();
        GroupSum threadGroupSum = new ThreadGroupSum(numbers);
        GroupSum executorGroupSum = new ExecutorGroupSum(numbers);
        GroupSum streamGroupSum = new ParallelStreamGroupSum(numbers);
        GroupSum customThreadPoolGroupSum = new CustomThreadPoolGroupSum(numbers);
        new GroupSumPerfomanceTest("ThreadGroupSum", threadGroupSum).runTest();
        new GroupSumPerfomanceTest("ExecutorGroupSum", executorGroupSum).runTest();
        new GroupSumPerfomanceTest("ParallelStreamGroupSum", streamGroupSum).runTest();
        new GroupSumPerfomanceTest("CustomThreadPoolGroupSum", customThreadPoolGroupSum).runTest();
    }

    private static void fillArray() {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                numbers[i][j] = random.nextInt();
            }
        }
    }
}
