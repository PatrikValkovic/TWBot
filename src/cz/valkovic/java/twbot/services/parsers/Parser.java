package cz.valkovic.java.twbot.services.parsers;

import java.net.URI;

public interface Parser {

    boolean willProccess(URI location);

    void proccess(URI location, String content);

}
