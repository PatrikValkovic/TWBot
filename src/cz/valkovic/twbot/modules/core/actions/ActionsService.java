package cz.valkovic.twbot.modules.core.actions;

import cz.valkovic.twbot.modules.core.observable.Observable;
import javafx.scene.web.WebEngine;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Allows to execute actions.
 */
public interface ActionsService {

    /**
     * Execute action and wait for explicit complete call.
     * @param action Action to perform. Receives {@link WebEngine} as the first parametr
     *               and callback as second, that needs to be call after action completion.
     */
    void action(BiConsumer<WebEngine, Runnable> action);

    /**
     * Execute action and wait for completion.
     * @param action Action to perform.
     */
    void action(Consumer<WebEngine> action);

    /**
     * Execute action and doesn't wait for completion.
     * @param action Action to perform. Receive WebEngine as the first parametr.
     */
    void actionWithoutWait(Consumer<WebEngine> action);

    /**
     * Exit the thread and join it.
     */
    void stopAndJoin();


    /**
     * Observable with information, whether has service some actions in queue or not.
     * @return Observable whether has service some actions in queue.
     */
    Observable<Boolean> hasActions();
}
