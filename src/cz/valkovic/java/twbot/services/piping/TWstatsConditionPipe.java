package cz.valkovic.java.twbot.services.piping;

import cz.valkovic.java.twbot.services.configuration.Configuration;
import cz.valkovic.java.twbot.services.piping.elementary.ConditionPipe;

import javax.inject.Inject;

public class TWstatsConditionPipe extends ConditionPipe {

    @Inject
    public TWstatsConditionPipe(Configuration conf) {
        super((url, s) -> url.getHost().equals(conf.twstatsDomain()), null);
    }
}
