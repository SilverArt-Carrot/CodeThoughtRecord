package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {

    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                int r = random.nextInt(10000);
                System.out.println(Thread.currentThread().getName() + ':' + r);
                return r;
            });

            futures.add(future);
        }

        int total = 0;
        for (CompletableFuture<Integer> future : futures) {
            try {
                Integer r = future.get();
                total += r;
            } catch (Exception e) {
                System.out.println("future get err:" + e.getMessage());
            }
//            Integer join = future.join();
//            System.out.println("join:" + join);
        }

        System.out.println("finish, total=" + total);
    }

    public static void test2() {
        CompletableFuture.supplyAsync(() -> {
            // 异步任务：生成一个随机数
//            int i = 1 / 0;  // 有异常会直接进入exceptionally
            return (int) (Math.random() * 100);
        }).thenApply(result -> {
            // 对异步任务的结果进行处理：加倍
            return result * 2;
        }).thenAccept(result -> {
            // 接收处理后的结果：打印
            System.out.println("处理后的结果：" + result);
        }).thenRun(() -> {
            // 在处理完成后执行的操作：打印完成信息
            System.out.println("处理完成");
        }).exceptionally(ex -> {
            // 异常处理：打印异常信息
            System.out.println("处理过程中发生异常：" + ex.getMessage());
            return null; // 返回一个默认值，以免异常影响后续操作
        });
    }
}
