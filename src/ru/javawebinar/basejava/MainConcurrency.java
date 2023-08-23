package ru.javawebinar.basejava;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private int counter;
    private static final Object LOCK = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) throws InterruptedException {
//        System.out.println(Thread.currentThread().getName());
//
        final MainConcurrency mainConcurrency = new MainConcurrency();
//        List<Thread> threads = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {

            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.submit(
//            Thread thread = new Thread(
                    () -> {
                        for (int j = 0; j < 100; j++) {
                            mainConcurrency.inc();
                        }
                        countDownLatch.countDown();
                    });
//            thread.start();
//            threads.add(thread);

        }
//        for (Thread t : threads) {
//            t.join();
//        }
        System.out.println(mainConcurrency.counter);
        countDownLatch.await();
        System.out.println(mainConcurrency.counter);

    }

    private synchronized void inc() {
//        synchronized (this) {
//        synchronized (MainConcurrency.class) {
//        System.out.println(Thread.currentThread().getName());
        counter++;
    }
}
