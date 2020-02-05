package cz.valkovic.twbot.services.piping.elementary;

import cz.valkovic.twbot.services.piping.ParsingPipe;
import java.net.URL;

public class TruePipe implements ParsingPipe {

    @Override
    public boolean process(URL location, String content) {
        return true;
    }
}
