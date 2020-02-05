package cz.valkovic.twbot.services.parsers;

import java.net.URL;
import org.jsoup.nodes.Document;

public interface Parser {

    boolean willProccess(URL location);

    void proccess(URL location, Document content);

}
