package cz.valkovic.java.twbot.services.piping;

import cz.valkovic.java.twbot.services.configuration.InterConfiguration;
import cz.valkovic.java.twbot.services.piping.elementary.ConditionPipe;

import javax.inject.Inject;

public class AppConditionPipe extends ConditionPipe {

    @Inject
    public AppConditionPipe(InterConfiguration interConf) {
        super((url, s) -> url.getHost().matches(interConf.appDomainRegex()), null);
    }
}
