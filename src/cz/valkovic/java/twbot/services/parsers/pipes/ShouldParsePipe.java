package cz.valkovic.java.twbot.services.parsers.pipes;

import cz.valkovic.java.twbot.services.logging.LoggingService;

import javax.inject.Inject;
import java.net.URL;

public class ShouldParsePipe implements ParsingPipe {

    private LoggingService log;

    @Inject
    public ShouldParsePipe(LoggingService log) {
        this.log = log;
    }

    @Override
    public boolean process(URL location, String content) {
        boolean willBeParsed = location.getHost().matches("cs\\d+\\.divokekmeny.cz") ||
                location.getHost().equals("www.twstats.com");

        if(willBeParsed)
            log.getPiping().info(location.toString() + " will be parsed");

        return willBeParsed;
    }
}
