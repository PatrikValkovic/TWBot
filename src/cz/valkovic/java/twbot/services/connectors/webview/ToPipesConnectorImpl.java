package cz.valkovic.java.twbot.services.connectors.webview;

import cz.valkovic.java.twbot.services.connectors.NavigationEngine;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import cz.valkovic.java.twbot.services.messaging.MessageService;
import cz.valkovic.java.twbot.services.messaging.messages.WebLoaded;
import cz.valkovic.java.twbot.services.piping.ParsingPipe;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.net.URL;

public class ToPipesConnectorImpl implements ToPipesConnector {

    @Inject
    public static void createPipes(ToPipesConnector c, LoggingService log) {
        log.getParsing().debug("ToPipesConnector created");
    }

    @Inject
    public ToPipesConnectorImpl(
            ParsingPipe pipe,
            LoggingService log,
            MessageService messaging,
            NavigationEngine navigation) {

        this.pipe = pipe;

        messaging.listenTo(WebLoaded.class, e -> {
            log.getPiping().info("Attempt to process " + navigation.getLocation());
            try {
                URL url = new URL(navigation.getLocation());
                this.pipe.process(url, navigation.getContent());
            }
            catch (MalformedURLException ex) {
                log.getPiping().info("Unable to parse URL");
                log.getPiping().debug(ex, ex);
            }
        });
    }

    private ParsingPipe pipe;

    @Override
    public void setParsingPipe(ParsingPipe pipe) {
        this.pipe = pipe;
    }
}
