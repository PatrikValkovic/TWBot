package cz.valkovic.java.twbot.services.connectors.webview;

import com.google.inject.assistedinject.Assisted;
import cz.valkovic.java.twbot.services.connectors.NavigationEngine;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import cz.valkovic.java.twbot.services.piping.ParsingPipe;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.net.URL;

public class ToPipesConnectorImpl implements ToPipesConnector {

    @Inject
    public ToPipesConnectorImpl(
            @Assisted NavigationEngine engine,
            ParsingPipe pipe,
            LoggingService log) {

        this.pipe = pipe;

        engine.loadedPageProperty().addListener((observable, oldValue, newValue) -> {
            log.getPiping().info("Attempt to process " + newValue);
            try {
                URL url = new URL(newValue);
                this.pipe.process(url, engine.getContent());
            }
            catch (MalformedURLException e) {
                log.getPiping().info("Unable to parse URL");
                log.getPiping().debug(e, e);
            }
        });
    }

    private ParsingPipe pipe;

    @Override
    public void setParsingPipe(ParsingPipe pipe) {
        this.pipe = pipe;
    }
}