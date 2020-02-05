package cz.valkovic.twbot.services.browserManipulation;

import javafx.scene.web.WebEngine;

public interface Actionable {

    WebEngine getEngine();

    Object getActionMonitor();

    void waitForMonitor() throws InterruptedException;
}
