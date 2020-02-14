package cz.valkovic.twbot.modules.parsing;

import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.core.timing.TimingRef;
import cz.valkovic.twbot.modules.core.timing.TimingService;
import cz.valkovic.twbot.modules.parsing.handle.ParsingRequestService;
import cz.valkovic.twbot.modules.parsing.setting.ParsingPublicSetting;
import java.time.Duration;
import javax.inject.Inject;

public class RepeatedParsingRegistration {

    @SuppressWarnings("FieldCanBeLocal")
    private static Object publicSettingLock = null;
    private static TimingRef callbackHandler = null;

    @Inject
    public static void register(
            SettingsProviderService setting,
            TimingService timing,
            ParsingRequestService parsing
    ){

        publicSettingLock = setting.observe(ParsingPublicSetting.class, (s) -> {
            if(callbackHandler != null)
                timing.remove(callbackHandler);
            callbackHandler = timing.executeEvery(parsing::parse, Duration.ofMillis(s.parseTime()));
        });

    }

}
