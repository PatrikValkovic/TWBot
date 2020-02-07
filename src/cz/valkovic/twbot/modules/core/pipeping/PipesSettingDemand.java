package cz.valkovic.twbot.modules.core.pipeping;

import cz.valkovic.twbot.modules.core.settings.SettingDemand;
import cz.valkovic.twbot.modules.core.settings.SettingRegistrationService;
import cz.valkovic.twbot.modules.core.settings.StorableSettings;
import javax.inject.Inject;

public class PipesSettingDemand extends SettingDemand {

    @Inject
    static void register(SettingRegistrationService set) {
        set.register(new PipesSettingDemand());
    }

    @Override
    public Class<? extends StorableSettings> getPrivateSetting() {
        return PipesPrivateSetting.class;
    }
}
