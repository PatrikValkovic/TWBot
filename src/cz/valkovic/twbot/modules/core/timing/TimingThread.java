package cz.valkovic.twbot.modules.core.timing;

import cz.valkovic.twbot.modules.core.execution.ExecutionService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.core.settings.instances.CorePrivateSetting;
import lombok.Getter;
import javax.inject.Inject;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class TimingThread implements Runnable {

    @Getter
    private final BlockingQueue<Object> queue = new PriorityBlockingQueue<>();

    @Getter
    private final AtomicBoolean exit = new AtomicBoolean(false);

    private final LoggingService log;
    private CorePrivateSetting setting;
    private final ExecutionService exe;

    @Inject
    public TimingThread(LoggingService log, SettingsProviderService setting, ExecutionService exe) {
        this.log = log;
        this.exe = exe;

        setting.observe(CorePrivateSetting.class, s -> this.setting = s);
    }

    @Override
    public void run() {
        this.log.getTiming().info("Starting timing thread");
        try {
            while (!this.exit.get()) {
                //TODO
                Thread.sleep(1000);
            }
        }
        catch(InterruptedException e){
            this.log.getTiming().error("Timing thread interrupted");
            this.log.getTiming().debug(e, e);
        }
        finally {
            this.log.getTiming().info("Timing thread done");
        }
    }
}
