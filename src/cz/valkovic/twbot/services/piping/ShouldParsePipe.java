package cz.valkovic.twbot.services.piping;

import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.core.settings.instances.CorePrivateSetting;
import javax.inject.Inject;
import java.net.URL;

public class ShouldParsePipe implements ParsingPipe {

    private LoggingService log;
    private CorePrivateSetting setting;

    @Inject
    public ShouldParsePipe(LoggingService log, SettingsProviderService settingsProvider) {
        this.log = log;
        settingsProvider.observe(CorePrivateSetting.class, s -> setting = s);
    }

    @Override
    public boolean process(URL location, String content) {
        boolean willBeParsed = location.getHost().matches(setting.appDomainRegex()) ||
                location.getHost().equals(setting.twstatsDomain());

        if (willBeParsed)
            log.getPipeping().info(location.toString() + " will be parsed");

        return willBeParsed;
    }
}
