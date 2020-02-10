package cz.valkovic.twbot.modules.parsing.handle;

import cz.valkovic.twbot.modules.core.actions.WebEngineProvider;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.pipeping.ParsingService;
import org.w3c.dom.Document;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Singleton
public class ParsingRequestServiceImpl implements ParsingRequestService {

    private final int MAX_FAILED_PARSING = 10;
    private int current_failed_parsing = 0;

    private final WebEngineProvider engineProvider;
    private final ParsingService parsing;
    private final LoggingService log;

    private final Transformer transformer;

    @Inject
    public ParsingRequestServiceImpl(
            WebEngineProvider engineProvider,
            ParsingService parsing,
            LoggingService log
    ) throws TransformerConfigurationException {
        this.engineProvider = engineProvider;
        this.parsing = parsing;
        this.log = log;

        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, StandardCharsets.UTF_8.name());
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        }
        catch(TransformerConfigurationException e){
            log.getParsing().error("Coudn't create transformer");
            log.getParsing().debug(e, e);
            throw e;
        }
    }

    @Override
    public void parse() {
        try {
            if(engineProvider.getEngine() == null){
                this.log.getParsing().debug("No engine yet, couldn't parse");
                return;
            }

            String location = engineProvider.getEngine().getLocation();
            String content = getContent();
            if(content == null){
                this.log.getParsing().debug("Couldn't obtain text out of the browser.");
                current_failed_parsing++;
                if(current_failed_parsing > MAX_FAILED_PARSING){
                    this.log.getParsing().error("Can't parse content of the browser.");
                }
            }
            current_failed_parsing = 0;
            log.getParsing().debug("Going to parse " + location);
            parsing.parse(location, content);
        }
        catch(MalformedURLException e) {
            log.getParsing().warn("URL is malformed " + engineProvider.getEngine().getLocation());
        }
        catch(TransformerConfigurationException e){
            log.getParsing().error("Couldn't create transformer");
            log.getParsing().debug(e, e);
        }
        catch(TransformerException e) {
            log.getParsing().warn("Exception during transformation");
            log.getParsing().debug(e, e);
        }
    }

    private String getContent() throws TransformerException {
        try {
            Document doc = engineProvider.getEngine().getDocument();
            ByteArrayOutputStream str = new ByteArrayOutputStream();
            transformer.transform(new DOMSource(doc), new StreamResult(str));
            return new String(str.toByteArray(), StandardCharsets.UTF_8);
        }
        catch(Exception e){
            return null;
        }
    }
}
