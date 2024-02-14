import java.util.concurrent.*;

public class TimedRun {

    private static final ExecutorService taskExec = Executors.newCachedThreadPool();

    public static void timeRun(Runnable r, long timeout, TimeUnit unit) {
        Future<?> task = taskExec.submit(r);
        try {
            task.get(timeout,unit);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            task.cancel(true);
        }
    }

}
