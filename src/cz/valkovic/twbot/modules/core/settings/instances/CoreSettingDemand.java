package cz.valkovic.twbot.modules.core.settings.instances;

import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.twbot.modules.core.events.instances.ApplicationCloseEvent;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.settings.*;
import javax.inject.Inject;
import javax.inject.Provider;
import java.util.concurrent.atomic.AtomicBoolean;

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
            AtomicBoolean runned = new AtomicBoolean(false);
            setting.get().observe(CorePrivateSetting.class, s -> {
                if(runned.get())
                    return;
                s.setProperty("firstRun", "false");
                log.getSettings().debug("Storing core setting");
                settingStorage.get().store();
                runned.set(true);
            });
        });
    }

    @Override
    public Class<? extends PublicSettings> getPublicSetting() {
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
