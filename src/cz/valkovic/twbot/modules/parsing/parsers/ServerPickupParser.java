package cz.valkovic.twbot.modules.parsing.parsers;

import cz.valkovic.twbot.modules.browsing.actions.ActionsWithReloadService;
import cz.valkovic.twbot.modules.core.pipeping.ParsingPipe;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.parsing.setting.ParsingPublicSetting;
import java.net.URL;
import javax.inject.Inject;
import org.jsoup.nodes.Document;

public class ServerPickupParser implements ParsingPipe {

    @SuppressWarnings("FieldCanBeLocal")
    private Object pubSettingHandler;
    private ParsingPublicSetting pubSetting;
    private final ActionsWithReloadService actions;

    @Inject
    public ServerPickupParser(SettingsProviderService setting, ActionsWithReloadService actions) {

        this.pubSettingHandler = setting.observe(ParsingPublicSetting.class, s -> this.pubSetting = s);
        this.actions = actions;
    }


    @Override
    public void process(URL url, Document content) {
        if (pubSetting.serverName() == null || pubSetting.serverName().length() == 0)
            return;

        actions.action(engine -> engine.load(url.toString() + "page/play/" + pubSetting.serverName()));
    }
}
