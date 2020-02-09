package cz.valkovic.twbot.modules.core.settings.instances;

import cz.valkovic.twbot.modules.core.settings.StorableSettings;
import org.aeonbits.owner.Config;

public interface CorePrivateSetting extends StorableSettings {

    @Config.DefaultValue("true")
    boolean firstRun();

    @Config.DefaultValue("10000")
    int maxLockWaitingTime();
}
