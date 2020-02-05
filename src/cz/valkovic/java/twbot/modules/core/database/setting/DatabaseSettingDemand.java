package cz.valkovic.java.twbot.modules.core.database.setting;

import cz.valkovic.java.twbot.modules.core.settings.SettingDemand;
import cz.valkovic.java.twbot.modules.core.settings.SettingRegistrationService;
import cz.valkovic.java.twbot.modules.core.settings.StorableSettings;
import javax.inject.Inject;

public class DatabaseSettingDemand extends SettingDemand {

    @Inject
    public static void register(SettingRegistrationService reg) {
        reg.register(new DatabaseSettingDemand());
    }

    @Override
    public Class<? extends StorableSettings> getPrivateSetting() {
        return DatabasePrivateSetting.class;
    }

    @Override
    public Class<?> getStaticSetting() {
        return DatabaseStaticSetting.class;
    }
}
