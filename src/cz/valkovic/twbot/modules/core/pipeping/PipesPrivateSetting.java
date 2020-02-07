package cz.valkovic.twbot.modules.core.pipeping;

import cz.valkovic.twbot.modules.core.settings.StorableSettings;

public interface PipesPrivateSetting extends StorableSettings {

    @DefaultValue("False")
    boolean storeUnparsableWebsites();
}
