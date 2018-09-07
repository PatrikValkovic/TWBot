package cz.valkovic.java.twbot.services.parsers;

import org.jsoup.nodes.Document;

import java.net.URL;

public interface Parser {

    boolean willProccess(URL location);

    void proccess(URL location, Document content);

}
