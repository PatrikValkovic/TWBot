package cz.valkovic.twbot.modules.setting;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import cz.valkovic.twbot.modules.core.importing.TWModule;
import cz.valkovic.twbot.modules.core.tabs.TabRegistrationService;

@TWModule
public class SettingModule extends AbstractModule {

    @Inject
    public static void register(TabRegistrationService tabs){
        tabs.register("Setting", SettingControl.class);
    }

    @Override
    protected void configure() {
        requestStaticInjection(SettingModule.class);
    }
}
