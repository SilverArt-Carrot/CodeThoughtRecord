package test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest {

    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        int taskNum = 50;

        CountDownLatch latch = new CountDownLatch(taskNum);
        for (int i = 0; i < taskNum; i++) {
            int finalI = i;
            pool.execute(() -> {
                System.out.println("task" + finalI + " finished");
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();
        System.out.println("all finished");
    }
}
