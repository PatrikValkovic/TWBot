package cz.valkovic.java.twbot.services.piping;

import cz.valkovic.java.twbot.modules.core.ResourceLoaderService;
import cz.valkovic.java.twbot.services.browserManipulation.ActionsService;
import cz.valkovic.java.twbot.services.configuration.Configuration;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class LoginPipe implements ParsingPipe {

    private final Configuration conf;
    private final ActionsService actionable;
    private final ResourceLoaderService resources;

    @Inject
    public LoginPipe(Configuration conf,
                     ActionsService actionable,
                     ResourceLoaderService resources) {
        this.conf = conf;
        this.actionable = actionable;
        this.resources = resources;
    }

    @Override
    public boolean process(URL location, String content) throws URISyntaxException, IOException {
        if(location.getHost().matches(conf.loginPageRegex())){

            if (conf.username() != null && conf.password() != null) {
                String script = resources.getResoureContent("scripts/loginScript.js");
                String toExecute = String.format(
                    script,
                    conf.username(),
                    conf.password()
                );
                actionable.performAction(e -> {
                    e.executeScript(toExecute);
                    return false;
                });
            }
            if (conf.serverName() != null) {
                String navigate = "window.location = '/page/play/" + conf.serverName() + "'";
                actionable.performWaitAction(e -> e.executeScript(navigate));
            }

        }
        return true;
    }
}
