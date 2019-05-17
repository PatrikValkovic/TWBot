package cz.valkovic.java.twbot.services.piping;

import cz.valkovic.java.twbot.services.ResourceLoaderService;
import cz.valkovic.java.twbot.services.browserManipulation.ActionsService;
import cz.valkovic.java.twbot.services.configuration.InterConfiguration;
import cz.valkovic.java.twbot.services.configuration.PublicConfiguration;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class LoginPipe implements ParsingPipe {

    private final InterConfiguration interConf;
    private final ActionsService actionable;
    private final PublicConfiguration pubConf;
    private final ResourceLoaderService resources;

    @Inject
    public LoginPipe(InterConfiguration interConf,
                     ActionsService actionable,
                     PublicConfiguration pubConf,
                     ResourceLoaderService resources) {
        this.interConf = interConf;
        this.actionable = actionable;
        this.pubConf = pubConf;
        this.resources = resources;
    }

    @Override
    public boolean process(URL location, String content) throws URISyntaxException, IOException {
        if(location.getHost().matches(interConf.loginPageRegex())){

            if (pubConf.username() != null && pubConf.password() != null) {
                String script = resources.getResoureContent("scripts/loginScript.js");
                String toExecute = String.format(
                    script,
                    pubConf.username(),
                    pubConf.password()
                );
                actionable.performAction(e -> {
                    e.executeScript(toExecute);
                    return false;
                });
            }
            if (pubConf.serverName() != null) {
                String navigate = "window.location = '/page/play/" + pubConf.serverName() + "'";
                actionable.performWaitAction(e -> e.executeScript(navigate));
            }

        }
        return true;
    }
}
