package cz.valkovic.java.twbot.services.browserManipulation;

import cz.valkovic.java.twbot.services.configuration.Configuration;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import cz.valkovic.java.twbot.services.messaging.MessageService;
import cz.valkovic.java.twbot.services.messaging.messages.PerformAction;
import cz.valkovic.java.twbot.services.messaging.messages.PerformNoWaitAction;
import cz.valkovic.java.twbot.services.messaging.messages.PerformWaitAction;
import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;
import java.util.function.Function;

@Singleton
public class ActionServiceImpl implements ActionsService {

    private Configuration conf;
    private LoggingService log;
    private final Object waitingLock = new Object();

    @Inject
    public ActionServiceImpl(Configuration conf,
                             LoggingService log,
                             MessageService message) {
        this.conf = conf;
        this.log = log;

        message.listenTo(PerformAction.class, e -> this.performAction(e.getAction()));
        message.listenTo(PerformNoWaitAction.class, e -> this.performNoWaitAction(e.getAction()));
        message.listenTo(PerformWaitAction.class, e -> this.performWaitAction(e.getAction()));
    }

    private BlockingQueue<ActionHelper> queue = new LinkedBlockingDeque<>();

    private static class ActionHelper implements Runnable {
        @Getter
        private boolean result;
        private Function<WebEngine, Boolean> method;
        @Setter
        private WebEngine engine;

        private ActionHelper(Function<WebEngine, Boolean> method) {
            this.method = method;
        }

        @Override
        public void run() {
            result = method.apply(engine);
            synchronized (this) {
                this.notify();
            }
        }
    }

    @Override
    public void bind(Actionable actionable) {

        Thread navigationThread = new Thread(() -> {
            Random rand = new Random(conf.seed());
            while (true) {
                int difference = Math.abs(conf.navigationTimeMax() - conf.navigationTimeMin());
                int toWait = rand.nextInt(difference) + conf.navigationTimeMin();

                try {
                    log.getAction().debug("Thread will sleep for " + toWait + " milliseconds");
                    synchronized (waitingLock) {
                        waitingLock.wait(toWait);
                    }

                    if (!queue.isEmpty()) {
                        Object monitor = actionable.getActionMonitor();
                        synchronized (monitor) {
                            ActionHelper h = queue.poll();
                            h.setEngine(actionable.getEngine());
                            log.getAction().info("Action will be performed");
                            synchronized (h) {
                                Platform.runLater(h);
                                h.wait();
                            }
                            boolean result = h.isResult();
                            if (result) {
                                log.getAction().debug("Waiting for page load because of action");
                                monitor.wait();
                                log.getAction().debug("Page loaded because of action");
                            } else
                                log.getAction().debug("The proccess wil not wait for action to reload the page");
                        }
                    }
                }
                catch (InterruptedException e) {
                    log.getAction().info("Ending action service");
                    log.getAction().debug(e, e);
                }
            }
        }, "Actionable thread");
        navigationThread.setDaemon(true);
        navigationThread.start();
    }

    @Override
    public void performWaitAction(Consumer<WebEngine> callback) {
        this.performAction(webEngine -> {
            callback.accept(webEngine);
            return true;
        });
    }

    @Override
    public void performNoWaitAction(Consumer<WebEngine> callback) {
        this.performAction(webEngine -> {
            callback.accept(webEngine);
            return false;
        });
    }

    @Override
    public void performAction(Function<WebEngine, Boolean> callback) {
        this.queue.add(new ActionHelper(callback));
    }

}
