package cz.valkovic.java.twbot.services.configuration;

import org.aeonbits.owner.Config;

public interface InterConfProps {

    @Config.DefaultValue("true")
    boolean firstRun();

    @Config.DefaultValue("cs\\d+\\.divokekmeny.cz")
    String appDomainRegex();

    @Config.DefaultValue("www.divokekmeny.cz")
    String loginPageRegex();

    @Config.DefaultValue("www.twstats.com")
    String twstatsDomain();

    @Config.DefaultValue("15000")
    int maxLockWaitingTime();
}
