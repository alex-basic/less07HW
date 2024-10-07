package ait.utils.pool;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadPool implements Executor {
    private Thread[] threads;
    private Queue<Runnable> workQueue = new ConcurrentLinkedQueue<>();
    private AtomicBoolean isRunning = new AtomicBoolean(true);

    public ThreadPool(int nThreads) {
        threads = new Thread[nThreads];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new TaskWorker());
            threads[i].start();
        }
    }

    public void shutDown() {
        isRunning.set(false);
    }

    public void joinToPool(long delay) throws InterruptedException {
        for (int i = 0; i < threads.length; i++) {
            threads[i].join(delay);
        }
    }


    @Override
    public void execute(Runnable command) {
        if (isRunning.get()) {
            workQueue.add(command);
        } else {
            throw new RejectedExecutionException();
        }
    }

    private class TaskWorker implements Runnable {

        @Override
        public void run() {
            while (isRunning.get() || !workQueue.isEmpty()) {
                Runnable task = workQueue.poll();
                if (task != null) {
                    task.run();
                }
            }
        }
    }
}
