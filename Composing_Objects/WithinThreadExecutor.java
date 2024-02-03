public class WithinThreadExecutor implements Executor{
    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
