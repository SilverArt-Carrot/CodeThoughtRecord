package thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABC {
    private static final int ITERATIONS = 10;
    private static final Lock lock = new ReentrantLock();
    private static final Condition conditionA = lock.newCondition();
    private static final Condition conditionB = lock.newCondition();
    private static final Condition conditionC = lock.newCondition();
    private static volatile int state = 0;

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            for (int i = 0; i < ITERATIONS; i++) {
                printLetter('A', 0);
            }
        });

        Thread threadB = new Thread(() -> {
            for (int i = 0; i < ITERATIONS; i++) {
                printLetter('B', 1);
            }
        });

        Thread threadC = new Thread(() -> {
            for (int i = 0; i < ITERATIONS; i++) {
                printLetter('C', 2);
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
    }

    private static void printLetter(char letter, int targetState) {
        lock.lock();
        try {
            while (state % 3 != targetState) {
                switch (targetState) {
                    case 0:
                        conditionA.await();
                        break;
                    case 1:
                        conditionB.await();
                        break;
                    case 2:
                        conditionC.await();
                        break;
                }
            }
            System.out.print(letter);
            state++;
            switch (state % 3) {
                case 0:
                    conditionA.signal();
                    break;
                case 1:
                    conditionB.signal();
                    break;
                case 2:
                    conditionC.signal();
                    break;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}

