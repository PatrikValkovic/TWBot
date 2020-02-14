package cz.valkovic.twbot.modules.parsing.locations;

import cz.valkovic.twbot.modules.core.pipeping.LocationPipe;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.parsing.setting.ParsingPrivateSetting;
import java.net.URL;
import javax.inject.Inject;
import org.jsoup.nodes.Document;

public class ApplicationLocPipe implements LocationPipe {

    public ParsingPrivateSetting privSetting;

    @Inject
    public ApplicationLocPipe(SettingsProviderService setting) {
        setting.observe(ParsingPrivateSetting.class, s -> privSetting = s);
    }

    @Override
    public boolean match(URL url, Document content) {
        return url.getHost().contains(privSetting.applicationDomain());
    }
}
