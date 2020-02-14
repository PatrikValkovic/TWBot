package cz.valkovic.twbot.modules.parsing.parsers;

import cz.valkovic.twbot.modules.core.ResourceLoaderService;
import cz.valkovic.twbot.modules.core.actions.ActionsService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.pipeping.ParsingPipe;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.parsing.setting.ParsingPublicSetting;
import java.net.URL;
import javax.inject.Inject;
import org.jsoup.nodes.Document;

public class LoginParser implements ParsingPipe {

    @SuppressWarnings("FieldCanBeLocal")
    private Object pubSettingHandler;
    private ParsingPublicSetting pubSetting;
    private final ResourceLoaderService resources;
    private final ActionsService actions;
    private final LoggingService log;

    @Inject
    public LoginParser(SettingsProviderService setting, ResourceLoaderService resources, ActionsService actions, LoggingService log) {

        this.pubSettingHandler = setting.observe(ParsingPublicSetting.class, s -> this.pubSetting = s);
        this.resources = resources;
        this.actions = actions;
        this.log = log;
    }


    @Override
    public void process(URL url, Document content) {
        if (pubSetting.username() == null || pubSetting.username().length() == 0 ||
                pubSetting.password() == null || pubSetting.password().length() == 0)
            return;

        try {
            String loginScript = resources.getResoureContent("scripts/loginScript.js");
            String filledScript = String.format(loginScript,
                    pubSetting.username(), pubSetting.password()
            );
            actions.action(engine -> engine.executeScript(filledScript));
        }
        catch(Exception e){
            this.log.getParsing().warn("Couldn't login user");
            this.log.getParsing().debug(e, e);
        }
    }
}
