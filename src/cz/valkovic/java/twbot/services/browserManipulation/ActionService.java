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
import java.util.function.Function;

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

    private BlockingQueue<Function<WebEngine, Boolean>> queue = new LinkedBlockingDeque<>();

    private static class ActionHelper implements Runnable{

        private boolean result;
        private Function<WebEngine, Boolean> method;
        private WebEngine engine;

        boolean getResult(){
            return result;
        }

        private ActionHelper(Function<WebEngine, Boolean> method, WebEngine engine) {
            this.engine = engine;
            this.method = method;
        }

        @Override
        public void run() {
            result = method.apply(engine);
            synchronized (this){
                this.notify();
            }
        }
    }

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
                            Function<WebEngine, Boolean> action = queue.poll();
                            log.getAction().info("Action will be performed ");
                            ActionHelper h = new ActionHelper(action, actionable.getEngine());
                            synchronized (h) {
                                Platform.runLater(h);
                                h.wait();
                            }
                            boolean result = h.getResult();
                            if(result) {
                                log.getAction().debug("Waiting for page load because of action");
                                monitor.wait();
                                log.getAction().debug("Page loaded because of action");
                            }
                            else
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
    public void performAction(Consumer<WebEngine> callback) {
        this.performAction(webEngine -> {
            callback.accept(webEngine);
            return true;
        });
    }

    @Override
    public void performAction(Function<WebEngine, Boolean> callback){
        this.queue.add(callback);
    }

}
