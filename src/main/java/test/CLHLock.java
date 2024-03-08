package test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * CLH锁
 * https://mp.weixin.qq.com/s/jEx-4XhNGOFdCo4Nou5tqg
 */
public class CLHLock {
    private final ThreadLocal<Node> node = ThreadLocal.withInitial(Node::new);
    private final AtomicReference<Node> tail = new AtomicReference<>(new Node());

    public void lock() {
        Node node = this.node.get();
        node.locked = true;
        Node pred = tail.getAndSet(node);
        while (pred.locked);
    }

    public void unlock() {
        Node node = this.node.get();
        node.locked = false;
        this.node.set(new Node());
    }

    private static class Node {
        private volatile boolean locked;
    }

    public static void main(String[] args) {
        int[] count = new int[]{0};
        ExecutorService pool = Executors.newFixedThreadPool(10);
        CLHLock lock = new CLHLock();

        CountDownLatch latch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            pool.execute(() -> {
                lock.lock();
                count[0] += 1;
                lock.unlock();
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("count:" + count[0]);
        pool.shutdown();
    }
}
