package cz.valkovic.twbot.services.piping;

import cz.valkovic.twbot.services.configuration.Configuration;
import cz.valkovic.twbot.services.piping.elementary.ConditionPipe;
import javax.inject.Inject;

public class AppConditionPipe extends ConditionPipe {

    @Inject
    public AppConditionPipe(Configuration conf) {
        super((url, s) -> url.getHost().matches(conf.appDomainRegex()), null);
    }
}
