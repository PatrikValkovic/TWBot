package cz.valkovic.java.twbot.services.piping;

import cz.valkovic.java.twbot.services.configuration.Configuration;
import cz.valkovic.java.twbot.modules.core.logging.LoggingService;

import javax.inject.Inject;
import java.net.URL;

public class ShouldParsePipe implements ParsingPipe {

    private LoggingService log;
    private Configuration conf;

    @Inject
    public ShouldParsePipe(LoggingService log, Configuration conf) {
        this.log = log;
        this.conf = conf;
    }

    @Override
    public boolean process(URL location, String content) {
        boolean willBeParsed = location.getHost().matches(conf.appDomainRegex()) ||
                location.getHost().equals(conf.twstatsDomain());

        if(willBeParsed)
            log.getPiping().info(location.toString() + " will be parsed");

        return willBeParsed;
    }
}
