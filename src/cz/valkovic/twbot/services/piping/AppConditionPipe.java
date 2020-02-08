package cz.valkovic.twbot.services.piping;

import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.core.settings.instances.CorePrivateSetting;
import cz.valkovic.twbot.services.piping.elementary.ConditionPipe;
import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicReference;

public class AppConditionPipe extends ConditionPipe {

    @Inject
    public AppConditionPipe(SettingsProviderService settingProvider) {
        super((url, content) -> {
            AtomicReference<String> domainRegex = new AtomicReference<>();
            settingProvider.observe(CorePrivateSetting.class, s -> domainRegex.set(s.appDomainRegex()));
            return url.getHost().matches(domainRegex.get());
        }, null);
    }
}
