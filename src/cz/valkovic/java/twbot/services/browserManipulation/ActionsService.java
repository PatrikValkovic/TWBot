package cz.valkovic.java.twbot.services.browserManipulation;

import cz.valkovic.java.twbot.services.connectors.webview.ToActionServiceConnector;
import javafx.scene.web.WebEngine;

import java.util.function.Consumer;
import java.util.function.Function;

public interface ActionsService extends ToActionServiceConnector {
    void performWaitAction(Consumer<WebEngine> callback);

    void performNoWaitAction(Consumer<WebEngine> callback);

    void performAction(Function<WebEngine, Boolean> callback);
}
