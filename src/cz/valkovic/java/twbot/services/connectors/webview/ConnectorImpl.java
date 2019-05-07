package cz.valkovic.java.twbot.services.connectors.webview;

import cz.valkovic.java.twbot.controls.MyWebView;
import cz.valkovic.java.twbot.services.configuration.Configuration;
import cz.valkovic.java.twbot.services.connectors.NavigationEngine;
import cz.valkovic.java.twbot.services.connectors.WebViewConnector;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import org.w3c.dom.Document;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

@Singleton
public class ConnectorImpl implements WebViewConnector, NavigationEngine {

    private LoggingService log;
    private ToActionServiceConnector actionConnector;
    private Configuration conf;

    private MyWebView view;


    @Inject
    public ConnectorImpl(
            ToActionServiceConnector actionConnector,
            Configuration conf,
            LoggingService log) {
        this.actionConnector = actionConnector;
        this.conf = conf;
        this.log = log;
    }

    @Override
    public void bind(MyWebView view) {
        this.view = view;
        this.view.getEngine().setUserAgent(conf.userAgent());
        actionConnector.bind(view);

    }

    @Override
    public String getContent() {
        Document doc = this.view.getEngine().getDocument();
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
        catch (TransformerException ex) {
            log.getParsing().warn("Unable to transform content into string");
            log.getParsing().debug(ex,ex);
            return "";
        }
    }

    @Override
    public String getLocation() {
        return this.view.getEngine().getLocation();
    }

    @Override
    public void setLocation(String location) {
        this.view.getEngine().load(location);
    }
}
