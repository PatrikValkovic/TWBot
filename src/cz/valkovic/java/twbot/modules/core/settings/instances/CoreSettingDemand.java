package cz.valkovic.java.twbot.modules.core.settings.instances;

import cz.valkovic.java.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.java.twbot.modules.core.events.instances.ApplicationCloseEvent;
import cz.valkovic.java.twbot.modules.core.logging.LoggingService;
import cz.valkovic.java.twbot.modules.core.settings.*;
import javax.inject.Inject;
import javax.inject.Provider;

public class CoreSettingDemand extends SettingDemand {

    @Inject
    public static void register(
            SettingRegistrationService reg,
            EventBrokerService event,
            Provider<SettingsProviderService> setting,
            Provider<SettingStorageService> settingStorage,
            LoggingService log){
        reg.register(new CoreSettingDemand());

        event.listenTo(ApplicationCloseEvent.class, c -> {
            setting.get().observe(CorePrivateSetting.class, s -> {
                s.setProperty("firstRun", "false");
                log.getSettings().debug("Storing core setting");
                settingStorage.get().store();
            });
        });
    }

    @Override
    public Class<? extends StorableSettings> getPublicSetting() {
        return CorePublicSetting.class;
    }

    @Override
    public Class<? extends StorableSettings> getPrivateSetting() {
        return CorePrivateSetting.class;
    }

    @Override
    public Class<?> getStaticSetting() {
        return CoreStaticSetting.class;
    }
}
