package cz.valkovic.twbot.modules.browsing.setting;

import cz.valkovic.twbot.modules.core.settings.PublicSettings;
import cz.valkovic.twbot.modules.core.settings.SettingDemand;
import cz.valkovic.twbot.modules.core.settings.SettingRegistrationService;
import javax.inject.Inject;

public class BrowsingSettingDemand extends SettingDemand {

    @Inject
    public static void register(SettingRegistrationService reg){
        reg.register(new BrowsingSettingDemand());
    }

    @Override
    public Class<? extends PublicSettings> getPublicSetting() {
        return BrowserPublicSetting.class;
    }
}
