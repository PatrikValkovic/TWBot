package cz.valkovic.java.twbot.services.parsers;

import org.jsoup.nodes.Document;

import java.net.URI;

public interface Parser {

    boolean willProccess(URI location);

    void proccess(URI location, Document content);

}
