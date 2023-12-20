public class NoVisibility {

    private static boolean ready;
    private static int number;

    private static class ReadeThread extends Thread {
        @Override
        public void run() {
            while (!ready)
                Thread.yield();
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReadeThread().start();
        number = 42;
        ready = true;
    }

}
