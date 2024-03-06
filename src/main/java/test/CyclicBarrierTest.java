package test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {
    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        ExecutorService pool = Executors.newFixedThreadPool(5);

        CyclicBarrier barrier = new CyclicBarrier(5, () -> {
            System.out.println("ok");
        });

        for (int i = 0; i < 50; i++) {
            int finalI = i;
            pool.execute(() -> {
                System.out.println("task" + finalI + " is finished");
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }

        pool.shutdown();
    }
}
