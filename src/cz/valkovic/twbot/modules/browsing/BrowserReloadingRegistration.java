package cz.valkovic.twbot.modules.browsing;

import cz.valkovic.twbot.modules.browsing.actions.ActionsWithReloadService;
import cz.valkovic.twbot.modules.browsing.events.PageLoadedEvent;
import cz.valkovic.twbot.modules.browsing.setting.BrowserPublicSetting;
import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.core.timing.TimingRef;
import cz.valkovic.twbot.modules.core.timing.TimingService;
import javafx.scene.web.WebEngine;
import javax.inject.Inject;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
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
        });

        Random rand = new Random();
        event.listenTo(PageLoadedEvent.class, e -> {
            if(reloadPageMax.get() == -1 || reloadPageMin.get() == -1)
                return;

            if(callbackHandler != null) {
                timing.remove(callbackHandler);
                callbackHandler = null;
            }

            int min = reloadPageMin.get(), max = reloadPageMax.get();
            int waitFor = rand.nextInt(max - min) + min;
            Instant executeAt = Instant.now().plus(Duration.ofMillis(waitFor));
            callbackHandler = timing.executeAt(() -> {
                action.action(WebEngine::reload);
            }, executeAt);
        });
    }

}
