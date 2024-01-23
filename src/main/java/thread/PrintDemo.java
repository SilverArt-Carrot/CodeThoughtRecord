package thread;


/**
 * 展示了3个线程轮流打印A,B,C
 */
public class PrintDemo {
    private static final Object lock = new Object();
    private String print_flag = "A";

    public void print(String flag) {
        synchronized (lock) {
            while (!flag.equals(print_flag)) {
                try {
                    lock.wait();  // wait() of lock
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.println(flag);
            if (flag.equals("A")) {
                print_flag = "B";
            } else if (flag.equals("B")) {
                print_flag = "C";
            } else {
                print_flag = "A";
            }
            lock.notifyAll(); // notifyAll() of lock
        }
    }

    public static void main(String[] args) {
        PrintDemo shardResource = new PrintDemo();

        Printer one = new Printer("A", shardResource);
        Printer two = new Printer("B", shardResource);
        Printer three = new Printer("C", shardResource);

        one.start();
        two.start();
        three.start();
    }

    public static class Printer extends Thread {
        private String flag;
        private PrintDemo shardResource;

        public Printer(String flag, PrintDemo shardResource) {
            this.flag = flag;
            this.shardResource = shardResource;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                shardResource.print(flag);
            }
        }
    }
}
