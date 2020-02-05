package cz.valkovic.twbot.modules.core.settings.instances;

import cz.valkovic.twbot.modules.core.settings.StorableSettings;
import org.aeonbits.owner.Config;

public interface CorePrivateSetting extends StorableSettings {

    @Config.DefaultValue("true")
    boolean firstRun();

    @Config.DefaultValue("cs\\d+\\.divokekmeny.cz")
    String appDomainRegex();

    @Config.DefaultValue("www.divokekmeny.cz")
    String loginPageRegex();

    @Config.DefaultValue("www.twstats.com")
    String twstatsDomain();

    @Config.DefaultValue("10000")
    int maxLockWaitingTime();
}
