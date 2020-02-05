package cz.valkovic.twbot.services.browserManipulation;

import java.util.function.Consumer;
import java.util.function.Function;
import javafx.scene.web.WebEngine;

public interface ActionsService {
    void performWaitAction(Consumer<WebEngine> callback);

    void performNoWaitAction(Consumer<WebEngine> callback);

    void performAction(Function<WebEngine, Boolean> callback);
}
