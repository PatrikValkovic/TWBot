package cz.valkovic.twbot.modules.browsing;

import cz.valkovic.twbot.modules.browsing.actions.ActionsWithReloadService;
import cz.valkovic.twbot.modules.browsing.events.PageLoadedEvent;
import cz.valkovic.twbot.modules.browsing.setting.BrowserPublicSetting;
import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.core.timing.TimingRef;
import cz.valkovic.twbot.modules.core.timing.TimingService;
import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicInteger;

public class BrowserReloadingRegistration {

    @SuppressWarnings("FieldCanBeLocal")
    private static Object publicSettingLock = null;
    private static TimingRef callbackHandler = null;

    private static AtomicInteger reloadPageMax = new AtomicInteger(-1);
    private static AtomicInteger reloadPageMin = new AtomicInteger(-1);

    @Inject
    public static void register(
            SettingsProviderService setting,
            ActionsWithReloadService action,
            EventBrokerService event,
            TimingService timing,
            LoggingService log
    ) {
        publicSettingLock = setting.observe(BrowserPublicSetting.class, s -> {
            reloadPageMax.set(s.reloadPageMax());
            reloadPageMin.set(s.reloadPageMin());
            //TODO
        });

        //TODO
        event.listenTo(PageLoadedEvent.class, e -> {});
    }

}
