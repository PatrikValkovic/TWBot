package cz.valkovic.twbot.modules.core.execution;

/**
 * Service that allows executing of action in separate thread as well in render thread.
 */
public interface ExecutionService {

    /**
     * Execute action in separate thread.
     * @param action Action to execute.
     */
    void run(Runnable action);

    /**
     * Execute action in the render thread.
     * @param action Action to execute.
     */
    void runInRender(Runnable action);

    /**
     * Finnish all the tasks and exit the application.
     */
    void stopAndJoin() throws InterruptedException;

}
