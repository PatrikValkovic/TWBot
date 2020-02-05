package cz.valkovic.java.twbot.modules.core.execution;

import cz.valkovic.java.twbot.modules.core.logging.LoggingService;
import javafx.application.Platform;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExecutionServiceImpl implements ExecutionService {

    private final LoggingService log;
    private final LockTimeProvider locktime;
    private final ExecutionThread execution;
    private final Thread thread;

    @Inject
    public ExecutionServiceImpl(LoggingService log, LockTimeProvider locktime) {
        this.log = log;
        this.locktime = locktime;
        this.execution = new ExecutionThread(this.log);
        this.thread = new Thread(execution, "ExecutionThread");
        this.thread.start();
    }

    //region interface
    /**
     * Execute action in separate thread.
     * @param action Action to execute.
     */
    @Override
    public void run(Runnable action) {
        this.log.getExecution().debug(String.format("Adding execution %s into the queue", action.getClass().getSimpleName()));
        this.execution.getQueue().add(action);
    }

    /**
     * Execute action in the render thread.
     * @param action Action to execute.
     */
    @Override
    public void runInRender(Runnable action) {
        this.run(() -> Platform.runLater(action));
    }

    /**
     * Finnish all the tasks and exit the application.
     */
    @Override
    public void waitAndExit() throws InterruptedException {
        this.log.getExecution().debug("Stopping execution service thread");

        this.execution.getEnd().set(true);
        int lock_time = this.locktime.getLockTime();
        this.thread.join(lock_time);

        if(this.thread.isAlive()){
            this.log.getExecution().warn("Execution thread didn't join, interrupting it now.");
            this.thread.interrupt();
            this.log.getExecution().debug("Execution thread interrupted.");
        }
        else {
            this.log.getExecution().debug("Execution thread joined.");
        }
    }
    //endregion
}
