package cz.valkovic.twbot.modules.core.timing;

import cz.valkovic.twbot.modules.core.execution.ExecutionService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import lombok.Getter;
import javax.inject.Inject;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class TimingThread implements Runnable {

    private final BlockingQueue<ThreadTempContainer> queue = new PriorityBlockingQueue<>();

    @Getter
    private final AtomicBoolean exit = new AtomicBoolean(false);

    private final LoggingService log;
    private final ExecutionService exe;

    @Inject
    public TimingThread(LoggingService log, ExecutionService exe) {
        this.log = log;
        this.exe = exe;
    }

    public TimingRef addCallback(Function<ExecutionService, Instant> callback, Instant when) {
        ThreadTempContainer tmp = new ThreadTempContainer(when, true, callback);
        synchronized (this.queue) {
            this.queue.add(tmp);
        }
        this.log.getTiming().debug(String.format(
                "Add timing callback to be executed in %d ms: %s",
                Duration.between(Instant.now(), when).toMillis(),
                callback.getClass().getCanonicalName()
        ));
        return tmp;
    }

    public void removeCallback(TimingRef ref) {
        ThreadTempContainer tmp = (ThreadTempContainer) ref;
        synchronized (this.queue) {
            if (!tmp.isInQueue()) {
                this.log.getTiming().debug(String.format(
                        "Timing callback already removed from the queue %s",
                        tmp.getCallback().getClass().getCanonicalName()
                ));
                return;
            }
            boolean removed;
            removed = this.queue.remove(tmp);
            if (!removed) {
                this.log.getTiming().warn(String.format(
                        "Callback wasn't for whatever reason removed from timing queue %s",
                        tmp.getCallback().getClass().getCanonicalName()
                ));
            }
            tmp.setInQueue(false);
        }
    }

    @Override
    public void run() {
        this.log.getTiming().info("Starting timing thread");
        try {
            while (!this.exit.get()) {
                ThreadTempContainer tmp;
                boolean tooEarly = true;

                synchronized (this.queue) {
                    tmp = this.queue.peek();
                    if (tmp != null) {
                        tmp = this.queue.poll();
                        tooEarly = tmp.getWhenToExecute().compareTo(Instant.now()) > 0;
                        if (!tooEarly) {

                            Instant nextExecution = tmp.getCallback().apply(this.exe);
                            if (nextExecution == null) {
                                this.log.getTiming().debug(String.format(
                                        "Timing action returned null and callback will not return to the queue %s",
                                        tmp.getCallback().getClass().getCanonicalName()
                                ));
                                tmp.setInQueue(false);
                                continue;
                            }
                            tmp.setWhenToExecute(nextExecution);
                            this.log.getTiming().debug(String.format(
                                    "Next callback will be executed in %d ms: %s",
                                    Duration.between(Instant.now(), tmp.getWhenToExecute()).toMillis(),
                                    tmp.getCallback().getClass().getCanonicalName()
                            ));

                        }
                        this.queue.add(tmp);
                    }
                }

                // no element in queue
                if (tmp == null) {
                    this.log.getTiming().debug("No element in the timing queue, looping");
                    Thread.sleep(1000);
                }
                // it is not time for the callback
                else if (tooEarly) {
                    this.log.getTiming().debug(String.format(
                            "Timing thread needs to wait %d ms for the next action %s",
                            Duration.between(Instant.now(), tmp.getWhenToExecute()).toMillis(),
                            tmp.getCallback().getClass().getCanonicalName()
                    ));
                    Thread.sleep(1000);
                }

            } // end while
        }
        catch (InterruptedException e) {
            this.log.getTiming().error("Timing thread interrupted");
            this.log.getTiming().debug(e, e);
        }
        finally {
            this.log.getTiming().info("Timing thread done");
        }
    }
}
