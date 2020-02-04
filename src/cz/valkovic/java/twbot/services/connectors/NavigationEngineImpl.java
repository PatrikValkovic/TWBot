package cz.valkovic.java.twbot.services.connectors;

import cz.valkovic.java.twbot.modules.core.logging.LoggingService;
import cz.valkovic.java.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.java.twbot.services.messaging.messages.PerformWaitAction;
import cz.valkovic.java.twbot.services.messaging.messages.WebLoaded;
import javafx.scene.web.WebEngine;
import org.w3c.dom.Document;

import javax.inject.Inject;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class NavigationEngineImpl implements NavigationEngine {

    private EventBrokerService messages;
    private LoggingService log;

    private final Object sync = new Object();
    private String content = "";
    private String url = "";

    @Inject
    public NavigationEngineImpl(EventBrokerService messages,
                                LoggingService log) {
        this.messages = messages;
        this.log = log;

        this.messages.listenTo(WebLoaded.class, (e) -> {
            synchronized (sync) {
                this.content = e.getContent();
                this.url = e.getLocation();
            }
        });
    }

    public static String contentFromEngine(WebEngine engine, LoggingService log)
    {
        Document doc = engine.getDocument();
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, StandardCharsets.UTF_8.name());
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            ByteArrayOutputStream str = new ByteArrayOutputStream();

            transformer.transform(new DOMSource(doc), new StreamResult(str));

            return new String(str.toByteArray(), StandardCharsets.UTF_8);
        }
        catch (TransformerException e) {
            log.getParsing().warn("Unable to transform content into string");
            log.getParsing().debug(e, e);
            return "";
        }
    }

    @Override
    public String getContent() {
        synchronized (this.sync){
            return this.content;
        }
    }

    @Override
    public String getLocation() {
        synchronized (this.sync){
            return this.url;
        }
    }

    @Override
    public void setLocation(String location) {
        log.getNavigationAction().debug("Attempt to set location to " + location);
        this.messages.invoke(new PerformWaitAction(engine -> {
            log.getNavigationAction().info("Navigating to " + location);
            engine.load(location);
        }));
    }
}
