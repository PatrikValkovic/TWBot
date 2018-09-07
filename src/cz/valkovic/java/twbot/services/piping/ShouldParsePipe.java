package cz.valkovic.java.twbot.services.piping;

import cz.valkovic.java.twbot.services.configuration.InterConfiguration;
import cz.valkovic.java.twbot.services.logging.LoggingService;

import javax.inject.Inject;
import java.net.URL;

public class ShouldParsePipe implements ParsingPipe {

    private LoggingService log;
    private InterConfiguration interConf;

    @Inject
    public ShouldParsePipe(LoggingService log, InterConfiguration interConf) {
        this.log = log;
        this.interConf = interConf;
    }

    @Override
    public boolean process(URL location, String content) {
        boolean willBeParsed = location.getHost().matches(interConf.appDomainRegex()) ||
                location.getHost().equals(interConf.twstatsDomain());

        if(willBeParsed)
            log.getPiping().info(location.toString() + " will be parsed");

        return willBeParsed;
    }
}
