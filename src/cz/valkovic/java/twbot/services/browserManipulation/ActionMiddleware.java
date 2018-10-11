package cz.valkovic.java.twbot.services.browserManipulation;

import cz.valkovic.java.twbot.services.connectors.webview.ToActionServiceConnector;
import javafx.scene.web.WebEngine;

import java.util.function.Consumer;

public interface ActionMiddleware extends ToActionServiceConnector {
    void performAction(Consumer<WebEngine> callback);
}
