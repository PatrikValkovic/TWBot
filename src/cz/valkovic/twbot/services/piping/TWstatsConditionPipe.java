package cz.valkovic.twbot.services.piping;

import cz.valkovic.twbot.services.configuration.Configuration;
import cz.valkovic.twbot.services.piping.elementary.ConditionPipe;
import javax.inject.Inject;

public class TWstatsConditionPipe extends ConditionPipe {

    @Inject
    public TWstatsConditionPipe(Configuration conf) {
        super((url, s) -> url.getHost().equals(conf.twstatsDomain()), null);
    }
}
