package cz.valkovic.twbot.modules.core.timing;

import com.google.inject.Injector;
import cz.valkovic.twbot.modules.core.execution.ExecutionService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.core.settings.instances.CorePrivateSetting;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

@Singleton
public class TimingServiceImpl implements TimingService {

    private final TimingThread timingThread;
    private final Thread thread;

    private final LoggingService log;
    private CorePrivateSetting setting;

    @Inject
    public TimingServiceImpl(Injector injector, LoggingService log, SettingsProviderService setting) {
        this.log = log;
        setting.observe(CorePrivateSetting.class, s -> this.setting = s);

        this.timingThread = injector.getInstance(TimingThread.class);
        this.thread = new Thread(this.timingThread, "TimingThread");
        this.thread.start();
    }

    @Override
    public TimingRef executeAt(Runnable callback, Instant when) {
        Function<ExecutionService, Instant> acc = (exe) -> {
            exe.run(callback);
            return null;
        };
        this.log.getTiming().debug(String.format(
                "Timing callback %s will be add as %s",
                callback.getClass().getCanonicalName(),
                acc.getClass().getCanonicalName()
        ));
        return this.timingThread.addCallback(acc, when);
    }

    @Override
    public TimingRef executeEveryWithDelay(Runnable callback, Duration duration, Instant firstExecution) {
        Function<ExecutionService, Instant> acc = (exe) -> {
            exe.run(callback);
            return Instant.now().plus(duration);
        };
        this.log.getTiming().debug(String.format(
                "Timing callback with delay %d ms to be repeated every %d ms, with name %s will be add as %s",
                Duration.between(Instant.now(), firstExecution).toMillis(),
                duration.toMillis(),
                callback.getClass().getCanonicalName(),
                acc.getClass().getCanonicalName()
        ));
        return this.timingThread.addCallback(acc, firstExecution);
    }

    @Override
    public void remove(TimingRef o) {
        this.timingThread.removeCallback(o);
    }

    @Override
    public void stopAndJoin() {
        this.log.getTiming().debug("Attempt to join timing thread.");
        this.timingThread.getExit().set(true);

        try {
            this.thread.join(this.setting.maxLockWaitingTime());
            if (this.thread.isAlive()) {
                this.log.getTiming().warn("Attempt to join timing thread failed, interrupting");
                this.thread.interrupt();
            }
        }
        catch (InterruptedException e) {
            this.log.getTiming().warn("Waiting for timing thread join was interrupted");
            this.log.getTiming().debug(e, e);
        }
    }
}
