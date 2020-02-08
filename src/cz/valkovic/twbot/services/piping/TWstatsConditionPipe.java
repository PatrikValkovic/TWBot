package cz.valkovic.twbot.services.piping;

import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.core.settings.instances.CorePrivateSetting;
import cz.valkovic.twbot.services.piping.elementary.ConditionPipe;
import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicReference;

public class TWstatsConditionPipe extends ConditionPipe {

    @Inject
    public TWstatsConditionPipe(SettingsProviderService settingsProvider) {
        super((url, content) -> {
            AtomicReference<String> twStatsDomain = new AtomicReference<>();
            settingsProvider.observe(CorePrivateSetting.class, s -> twStatsDomain.set(s.twstatsDomain()));
            return url.getHost().matches(twStatsDomain.get());
        }, null);
    }
}
