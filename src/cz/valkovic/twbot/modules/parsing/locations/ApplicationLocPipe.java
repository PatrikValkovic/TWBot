package cz.valkovic.twbot.modules.parsing.locations;

import cz.valkovic.twbot.modules.core.pipeping.LocationPipe;
import cz.valkovic.twbot.modules.core.pipeping.PIPES;
import cz.valkovic.twbot.modules.core.pipeping.PipesRegistrationService;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.parsing.LOCATIONS;
import cz.valkovic.twbot.modules.parsing.setting.ParsingPrivateSetting;
import org.jsoup.nodes.Document;
import javax.inject.Inject;
import java.net.URL;

public class ApplicationLocPipe implements LocationPipe {

    @Inject
    public static void register(PipesRegistrationService pipes){
        pipes.registerLocationPipe(
                PIPES.EVERYTHING.toString(),
                LOCATIONS.APPLICATION.toString(),
                ApplicationLocPipe.class
        );
    }


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
