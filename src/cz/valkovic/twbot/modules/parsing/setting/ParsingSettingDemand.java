package cz.valkovic.twbot.modules.parsing.setting;

import cz.valkovic.twbot.modules.core.settings.SettingDemand;
import cz.valkovic.twbot.modules.core.settings.SettingRegistrationService;
import cz.valkovic.twbot.modules.core.settings.StorableSettings;
import javax.inject.Inject;

public class ParsingSettingDemand extends SettingDemand {

    @Inject
    public static void register(SettingRegistrationService setting){
        setting.register(new ParsingSettingDemand());
    }

    @Override
    public Class<? extends StorableSettings> getPrivateSetting() {
        return ParsingPrivateSetting.class;
    }
}
