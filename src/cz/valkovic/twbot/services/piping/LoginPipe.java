package cz.valkovic.twbot.services.piping;

import cz.valkovic.twbot.modules.core.ResourceLoaderService;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.core.settings.instances.CorePrivateSetting;
import cz.valkovic.twbot.modules.core.settings.instances.CorePublicSetting;
import cz.valkovic.twbot.services.browserManipulation.ActionsService;
import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class LoginPipe implements ParsingPipe {

    private final ActionsService actionable;
    private final ResourceLoaderService resources;

    private CorePrivateSetting privSetting;
    private CorePublicSetting setting;
    @SuppressWarnings("FieldCanBeLocal")
    private Object settingLock;

    @Inject
    public LoginPipe(SettingsProviderService settingsProvider,
                     ActionsService actionable,
                     ResourceLoaderService resources) {
        this.actionable = actionable;
        this.resources = resources;

        this.settingLock = settingsProvider.observe(CorePublicSetting.class, s -> setting = s);
        settingsProvider.observe(CorePrivateSetting.class, s -> privSetting = s);
    }

    @Override
    public boolean process(URL location, String content) throws URISyntaxException, IOException {
        if(location.getHost().matches(privSetting.loginPageRegex())){

            if (setting.username() != null && setting.password() != null) {
                String script = resources.getResoureContent("scripts/loginScript.js");
                String toExecute = String.format(
                    script,
                    setting.username(),
                    setting.password()
                );
                actionable.performAction(e -> {
                    e.executeScript(toExecute);
                    return false;
                });
            }
            if (setting.serverName() != null) {
                String navigate = "window.location = '/page/play/" + setting.serverName() + "'";
                actionable.performWaitAction(e -> e.executeScript(navigate));
            }

        }
        return true;
    }
}
