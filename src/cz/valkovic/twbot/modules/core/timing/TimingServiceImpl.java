package cz.valkovic.twbot.modules.core.timing;

import com.google.inject.Injector;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.core.settings.instances.CorePrivateSetting;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Duration;
import java.time.Instant;

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
        //TODO
        return new TimingRef() {
        };
    }

    @Override
    public TimingRef executeEvery(Runnable callback, Duration duration) {
        //TODO
        return new TimingRef() {
        };
    }

    @Override
    public TimingRef executeEveryWithDelay(Runnable callback, Duration duration, Instant firstExecution) {
        //TODO
        return new TimingRef() {
        };
    }

    @Override
    public void remove(TimingRef o) {
        //TODO
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
