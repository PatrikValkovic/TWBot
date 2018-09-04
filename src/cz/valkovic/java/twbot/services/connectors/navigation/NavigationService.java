package cz.valkovic.java.twbot.services.connectors.navigation;

import cz.valkovic.java.twbot.services.configuration.Configuration;
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

    @Inject
    public NavigationService(Configuration configuration, LoggingService log) {
        this.configuration = configuration;
        this.log = log;
    }

    Navigatable navigatable = null;

    BlockingQueue<String> queue = new LinkedBlockingDeque<>();

    @Override
    public void bind(Navigatable navigatable) {
        this.navigatable = navigatable;

        Thread navigationThread = new Thread(() -> {

            //TODO fixed seed
            Random rand = new Random();

            while(true) {
                int toWait = rand.nextInt(Math.abs(configuration.navigationTimeMax() - configuration.navigationTimeMin())) + configuration.navigationTimeMin();

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
                    log.getNavigating().warn("Error when navigating");
                    log.getNavigating().debug(e, e);
                    //TODO proper end
                }

            }
        });
        navigationThread.start();
    }

    @Override
    public NavigationMiddleware queue(String... urls) {
        this.queue.addAll(Arrays.asList(urls));
        return this;
    }
}
