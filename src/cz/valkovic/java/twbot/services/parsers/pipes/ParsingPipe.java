package cz.valkovic.java.twbot.services.parsers.pipes;

import java.net.URL;

public interface ParsingPipe {

    boolean process(URL location, String content);

}
