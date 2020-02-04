package cz.valkovic.java.twbot.services.configuration;

import cz.valkovic.java.twbot.modules.core.settings.instances.CorePrivateSetting;
import cz.valkovic.java.twbot.modules.core.settings.instances.CorePublicSetting;
import java.util.Random;

public interface Configuration extends
        CorePublicSetting,
        CorePrivateSetting {

    int majorVersion();

    int minorVersion();

    int patchVersion();

    default String version() {
        return majorVersion() + "." + minorVersion() + "." + patchVersion();
    }

    default long seed() {
        Random rand = new Random();
        return rand.nextLong();
    }

}
