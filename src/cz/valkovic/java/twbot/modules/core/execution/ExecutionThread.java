package cz.valkovic.java.twbot.modules.core.execution;


import cz.valkovic.java.twbot.modules.core.logging.LoggingService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.Getter;

public class ExecutionThread implements Runnable {

    private LoggingService log;

    @Getter
    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    @Getter
    private final AtomicBoolean end = new AtomicBoolean(false);


    public ExecutionThread(LoggingService log) {
        this.log = log;
    }

    @Override
    public void run() {
        try {
            while (!this.end.get() || !this.queue.isEmpty()) {
                Runnable to_execute = this.queue.poll(1000, TimeUnit.MILLISECONDS);
                if(to_execute == null){
                    this.log.getExecution().debug("No execution in queue, looping.");
                    continue;
                }
                try {
                    this.log.getExecution().debug(String.format(
                            "Running execution %s",
                            to_execute.getClass().getCanonicalName()
                    ));
                    to_execute.run();
                    this.log.getExecution().debug(String.format(
                            "Execution executed %s",
                            to_execute.getClass().getCanonicalName()
                    ));
                }
                catch(Exception e) {
                    this.log.getExecution().debug(String.format(
                            "Error in execution %s",
                            to_execute.getClass().getCanonicalName()
                    ), e);
                }
            }
        }
        catch (InterruptedException e) {
            this.log.getExecution().warn("Execution thread interrupted during execution");
        }
    }
}
