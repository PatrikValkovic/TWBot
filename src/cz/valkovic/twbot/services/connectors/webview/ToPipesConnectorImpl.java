package cz.valkovic.twbot.services.connectors.webview;

import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.services.messaging.messages.WebLoaded;
import cz.valkovic.twbot.services.piping.ParsingPipe;
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
            EventBrokerService messaging) {

        this.pipe = pipe;

        messaging.listenTo(WebLoaded.class, e -> {
            log.getPipeping().info("Attempt to process " + e.getLocation());
            try {
                URL url = new URL(e.getLocation());
                this.pipe.process(url, e.getContent());
            }
            catch (MalformedURLException ex) {
                log.getPipeping().info("Unable to parse URL");
                log.getPipeping().debug(ex, ex);
            }
        });
    }

    private ParsingPipe pipe;

    @Override
    public void setParsingPipe(ParsingPipe pipe) {
        this.pipe = pipe;
    }
}
