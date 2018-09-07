package cz.valkovic.java.twbot.services.piping;

import cz.valkovic.java.twbot.services.configuration.InterConfiguration;
import cz.valkovic.java.twbot.services.piping.elementary.ConditionPipe;

import javax.inject.Inject;

public class TWstatsConditionPipe extends ConditionPipe {

    @Inject
    public TWstatsConditionPipe(InterConfiguration interConfiguration) {
        super((url, s) -> url.getHost().equals(interConfiguration.twstatsDomain()), null);
    }
}
