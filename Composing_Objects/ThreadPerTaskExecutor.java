public class ThreadPerTaskExecutor implements Executor{
    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}
