package cz.valkovic.twbot.services.piping;

import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.services.configuration.Configuration;
import java.net.URL;
import javax.inject.Inject;

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
