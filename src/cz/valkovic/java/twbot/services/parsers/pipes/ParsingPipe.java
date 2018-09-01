package cz.valkovic.java.twbot.services.parsers.pipes;

import java.net.URL;

public interface ParsingPipe {

    void process(URL location, String content);

}
