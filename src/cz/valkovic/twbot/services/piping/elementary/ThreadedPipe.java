package cz.valkovic.twbot.services.piping.elementary;

import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.twbot.modules.core.events.instances.ApplicationCloseEvent;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.core.settings.instances.CorePrivateSetting;
import cz.valkovic.twbot.services.piping.ParsingPipe;
import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import javax.inject.Inject;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadedPipe implements ParsingPipe {

    private LoggingService log;
    private CorePrivateSetting privSet;

    @Inject
    public ThreadedPipe(SettingsProviderService settingsProvider,
                        EventBrokerService messages,
                        LoggingService log) {
        this.log = log;

        this.t = new ParsingThread(settingsProvider, log);
        this.t.start();

        settingsProvider.observe(CorePrivateSetting.class, s -> privSet = s);

        messages.listenTo(ApplicationCloseEvent.class, e -> {
            this.t.getWorking().set(false);
            this.t.join(privSet.maxLockWaitingTime());
            if(this.t.isAlive()){
                log.getPipeping().warn("Couldn't join piping thread");
                this.t.interrupt();
            }
            else {
                log.getPipeping().info("Piping thread joined");
            }
        });
    }

    public ThreadedPipe to(ParsingPipe pipe) {
        synchronized (this.t.getSync()){
            this.t.setPipe(pipe);
        }
        return this;
    }

    @Override
    public boolean process(URL location, String content) throws Exception {
        if(!this.t.getToProcess().offer(new Pair<>(location, content), privSet.maxLockWaitingTime(), TimeUnit.MILLISECONDS)){
            this.log.getPipeping().warn("Couldn't insert new content to the piping thread");
        } else {
            this.log.getPipeping().debug("Content inserted to the piping thread");
        }
        return true;
    }


    private ParsingThread t;
    private class ParsingThread extends Thread {
        @Getter
        private final BlockingQueue<Pair<URL, String>> toProcess = new LinkedBlockingDeque<>();
        @Getter
        private final AtomicBoolean working = new AtomicBoolean(true);

        @Getter
        private final Object sync = new Object();
        @Getter
        @Setter
        private ParsingPipe pipe;

        private LoggingService log;
        private CorePrivateSetting setting;

        ParsingThread(SettingsProviderService settingProvider,
                     LoggingService log)
        {
            this.log = log;

            this.setName("Piping thread");

            settingProvider.observe(CorePrivateSetting.class, s -> setting = s);
        }

        @Override
        public void run() {
            this.log.getPipeping().debug("Starting piping thread");
            while(this.working.get() || !toProcess.isEmpty()){
                try {
                    Pair<URL, String> next =
                            this.toProcess.poll(this.setting.maxLockWaitingTime() / 4, TimeUnit.MILLISECONDS);
                    if (next == null) {
                        continue;
                    }
                    synchronized (this.getSync()) {
                        try {
                            this.pipe.process(next.getKey(), next.getValue());
                        }
                        catch (Exception e) {
                            log.getPipeping().warn("Exception during handling piping");
                            log.getPipeping().debug(e,e );
                        }
                    }
                }
                catch(InterruptedException e){
                    log.getPipeping().warn("Piping thread interrupted, thread is ending");
                    log.getPipeping().debug(e, e);
                }
            }
        }
    }
}
