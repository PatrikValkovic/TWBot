package cz.valkovic.java.twbot.services.navigation;

import cz.valkovic.java.twbot.services.configuration.Configuration;
import cz.valkovic.java.twbot.services.configuration.InterConfiguration;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import javafx.application.Platform;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Singleton
public class NavigationService implements NavigationMiddleware {

    private Configuration configuration;
    private LoggingService log;
    private InterConfiguration interConfiguration;

    @Inject
    public NavigationService(Configuration configuration, LoggingService log, InterConfiguration interConfiguration) {
        this.configuration = configuration;
        this.log = log;
        this.interConfiguration = interConfiguration;
    }

    private BlockingQueue<String> queue = new LinkedBlockingDeque<>();

    @Override
    public void bind(Navigatable navigatable) {

        Thread navigationThread = new Thread(() -> {
            Random rand = new Random(interConfiguration.seed());
            while (true) {
                int difference = Math.abs(configuration.navigationTimeMax() - configuration.navigationTimeMin());
                int toWait = rand.nextInt(difference) + configuration.navigationTimeMin();

                try {
                    log.getNavigating().debug("Thread will sleep for " + toWait + " milliseconds");
                    Thread.sleep(toWait);

                    if (!queue.isEmpty()) {
                        Object monitor = navigatable.getNavigationMonitor();
                        synchronized (monitor) {
                            String url = queue.poll();
                            log.getNavigating().info("App will be navigated to " + url);
                            Platform.runLater(() -> {
                                navigatable.setLocation(url);
                            });
                            log.getNavigating().debug("Waiting for page load");
                            monitor.wait();
                            log.getNavigating().debug("Page loaded");
                        }
                    }
                }
                catch (InterruptedException e) {
                    log.getNavigating().info("Ending navigating service");
                    log.getNavigating().debug(e, e);
                }
            }
        });
        navigationThread.setDaemon(true);
        navigationThread.start();
    }

    @Override
    public NavigationMiddleware queue(String... urls) {
        this.queue.addAll(Arrays.asList(urls));
        return this;
    }
}
