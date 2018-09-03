package cz.valkovic.java.twbot.services.parsers.pipes;

import java.net.URL;

public class FalsePipe implements ParsingPipe {

    @Override
    public boolean process(URL location, String content) {
        return false;
    }
}
