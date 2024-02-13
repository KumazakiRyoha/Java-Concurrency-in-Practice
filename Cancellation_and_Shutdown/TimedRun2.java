import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class TimedRun2 {

    private static final ScheduledExecutorService cancelExec
            = newScheduledThreadPool(1);

    public static void timeRun(final Runnable r,
                               long timeout, TimeUnit unit) throws InterruptedException {
        class RethrowableTask implements Runnable {
            private volatile Throwable t;
            @Override
            public void run() {
                try {
                    r.run();
                } catch (Throwable t) {
                    this.t = t;
                }
            }

            void rethrow() {
                if (t != null) {

                }
            }
        }
        RethrowableTask task  = new RethrowableTask();
        final Thread taskThread = new Thread(task);
        taskThread.start();
        cancelExec.schedule(() -> taskThread.interrupt(),timeout,unit);
        taskThread.join(unit.toMillis(timeout));
        task.rethrow();
    }

}
