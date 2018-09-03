package cz.valkovic.java.twbot.services.connectors.navigation;

import javafx.application.Platform;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Singleton
public class NavigationService implements NavigationMiddleware {

    Navigatable navigatable = null;

    BlockingQueue<String> queue = new LinkedBlockingDeque<>();

    @Override
    public void bind(Navigatable navigatable) {
        this.navigatable = navigatable;

        //TODO
        new Thread(() -> {
            try {
                while(true) {
                    Thread.sleep(10 * 1000);

                    if (!queue.isEmpty())
                        Platform.runLater(() -> {
                            navigatable.setLocation(queue.poll());
                        });
                }
            }
            catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public NavigationMiddleware queue(String... urls) {
        this.queue.addAll(Arrays.asList(urls));
        return this;
    }
}
