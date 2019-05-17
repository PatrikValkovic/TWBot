package cz.valkovic.java.twbot.services.piping;

import cz.valkovic.java.twbot.services.configuration.Configuration;
import cz.valkovic.java.twbot.services.piping.elementary.ConditionPipe;

import javax.inject.Inject;

public class AppConditionPipe extends ConditionPipe {

    @Inject
    public AppConditionPipe(Configuration conf) {
        super((url, s) -> url.getHost().matches(conf.appDomainRegex()), null);
    }
}
