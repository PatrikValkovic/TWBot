package cz.valkovic.twbot.services.piping;

import java.net.URL;

public interface ParsingPipe {

    boolean process(URL location, String content) throws Exception;

}
