package cz.valkovic.java.twbot.services.piping;

import cz.valkovic.java.twbot.services.ResourceLoaderService;
import cz.valkovic.java.twbot.services.browserManipulation.ActionMiddleware;
import cz.valkovic.java.twbot.services.configuration.Configuration;
import cz.valkovic.java.twbot.services.configuration.InterConfiguration;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class LoginPipe implements ParsingPipe {

    private final InterConfiguration interConf;
    private final ActionMiddleware actionable;
    private final Configuration conf;
    private final ResourceLoaderService resources;

    @Inject
    public LoginPipe(InterConfiguration interConf,
                     ActionMiddleware actionable,
                     Configuration conf,
                     ResourceLoaderService resources) {
        this.interConf = interConf;
        this.actionable = actionable;
        this.conf = conf;
        this.resources = resources;
    }

    @Override
    public boolean process(URL location, String content) throws URISyntaxException, IOException {
        if(location.getHost().matches(interConf.loginPageRegex())){

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
                actionable.performAction(e -> {
                    e.executeScript(navigate);
                });
            }

        }
        return true;
    }
}
