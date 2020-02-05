package cz.valkovic.twbot.services.piping.elementary;

import cz.valkovic.twbot.services.piping.ParsingPipe;
import java.net.URL;

public class FalsePipe implements ParsingPipe {

    @Override
    public boolean process(URL location, String content) {
        return false;
    }
}
