package cz.valkovic.java.twbot.services.browserManipulation;

import cz.valkovic.java.twbot.services.configuration.Configuration;
import cz.valkovic.java.twbot.services.configuration.InterConfiguration;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import javafx.application.Platform;
import javafx.scene.web.WebEngine;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

@Singleton
public class ActionService implements ActionMiddleware {

    private Configuration configuration;
    private LoggingService log;
    private InterConfiguration interConfiguration;

    @Inject
    public ActionService(Configuration configuration, LoggingService log, InterConfiguration interConfiguration) {
        this.configuration = configuration;
        this.log = log;
        this.interConfiguration = interConfiguration;
    }

    private BlockingQueue<Consumer<WebEngine>> queue = new LinkedBlockingDeque<>();

    @Override
    public void bind(Actionable actionable) {

        Thread navigationThread = new Thread(() -> {
            Random rand = new Random(interConfiguration.seed());
            while (true) {
                int difference = Math.abs(configuration.navigationTimeMax() - configuration.navigationTimeMin());
                int toWait = rand.nextInt(difference) + configuration.navigationTimeMin();

                try {
                    log.getAction().debug("Thread will sleep for " + toWait + " milliseconds");
                    Thread.sleep(toWait);

                    if (!queue.isEmpty()) {
                        Object monitor = actionable.getActionMonitor();
                        synchronized (monitor) {
                            Consumer<WebEngine> action = queue.poll();
                            log.getAction().info("Action will be performed ");
                            Platform.runLater(() -> {
                                action.accept(actionable.getEngine());
                            });
                            log.getAction().debug("Waiting for page load because of action");
                            monitor.wait();
                            log.getAction().debug("Page loaded because of action");
                        }
                    }
                }
                catch (InterruptedException e) {
                    log.getAction().info("Ending action service");
                    log.getAction().debug(e, e);
                }
            }
        });
        navigationThread.setDaemon(true);
        navigationThread.start();
    }

    @Override
    public void performAction(Consumer<WebEngine> callback) {
        this.queue.add(callback);
    }

}
