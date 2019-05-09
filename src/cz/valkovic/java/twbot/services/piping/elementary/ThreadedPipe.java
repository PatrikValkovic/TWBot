package cz.valkovic.java.twbot.services.piping.elementary;

import cz.valkovic.java.twbot.services.configuration.InterConfiguration;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import cz.valkovic.java.twbot.services.messaging.MessageService;
import cz.valkovic.java.twbot.services.messaging.messages.ApplicationClosing;
import cz.valkovic.java.twbot.services.piping.ParsingPipe;
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

    private InterConfiguration interConf;
    private LoggingService log;

    @Inject
    public ThreadedPipe(InterConfiguration interConf,
                        MessageService messages,
                        LoggingService log) {
        this.interConf = interConf;
        this.log = log;

        this.t = new ParsingThread(interConf, log);
        this.t.start();

        messages.listenTo(ApplicationClosing.class, e -> {
            this.t.getWorking().set(false);
            this.t.join(interConf.maxLockWaitingTime());
            if(this.t.isAlive()){
                log.getPiping().warn("Couldn't join piping thread");
                this.t.interrupt();
            }
            else {
                log.getPiping().info("Piping thread joined");
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
        if(!this.t.getToProcess().offer(new Pair<>(location, content), interConf.maxLockWaitingTime(), TimeUnit.MILLISECONDS)){
            this.log.getPiping().warn("Couldn't insert new content to the piping thread");
        } else {
            this.log.getPiping().debug("Content inserted to the piping thread");
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

        private InterConfiguration interConf;
        private LoggingService log;

        ParsingThread(InterConfiguration interConf,
                             LoggingService log)
        {
            this.interConf = interConf;
            this.log = log;

            this.setName("Piping thread");
        }

        @Override
        public void run() {
            this.log.getPiping().debug("Starting piping thread");
            while(this.working.get() || !toProcess.isEmpty()){
                try {
                    Pair<URL, String> next =
                            this.toProcess.poll(this.interConf.maxLockWaitingTime() / 4, TimeUnit.MILLISECONDS);
                    if (next == null) {
                        continue;
                    }
                    synchronized (this.getSync()) {
                        try {
                            this.pipe.process(next.getKey(), next.getValue());
                        }
                        catch (Exception e) {
                            log.getPiping().warn("Exception during handling piping");
                            log.getPiping().debug(e,e );
                        }
                    }
                }
                catch(InterruptedException e){
                    log.getPiping().warn("Piping thread interrupted, thread is ending");
                    log.getPiping().debug(e, e);
                }
            }
        }
    }
}
