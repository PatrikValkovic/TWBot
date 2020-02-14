package cz.valkovic.twbot.modules.parsing.locations;

import cz.valkovic.twbot.modules.core.pipeping.LocationPipe;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.parsing.setting.ParsingPrivateSetting;
import java.net.URL;
import javax.inject.Inject;
import org.jsoup.nodes.Document;

public class IngameLocPipe implements LocationPipe {

    ParsingPrivateSetting setting;

    @Inject
    public IngameLocPipe(SettingsProviderService setting){
        setting.observe(ParsingPrivateSetting.class, s -> this.setting = s);
    }

    @Override
    public boolean match(URL url, Document content) {
        return url.getHost().matches(setting.ingameRegex());
    }
}
