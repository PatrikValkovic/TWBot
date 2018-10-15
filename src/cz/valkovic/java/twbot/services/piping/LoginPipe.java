package cz.valkovic.java.twbot.services.piping;

import cz.valkovic.java.twbot.services.browserManipulation.ActionMiddleware;
import cz.valkovic.java.twbot.services.configuration.Configuration;
import cz.valkovic.java.twbot.services.configuration.InterConfiguration;

import javax.inject.Inject;
import java.net.URL;

public class LoginPipe implements ParsingPipe {

    private final InterConfiguration interConf;
    private final ActionMiddleware actionable;
    private final Configuration conf;

    @Inject
    public LoginPipe(InterConfiguration interConf, ActionMiddleware actionable, Configuration conf) {
        this.interConf = interConf;
        this.actionable = actionable;
        this.conf = conf;
    }

    @Override
    public boolean process(URL location, String content) {
        if(location.getHost().matches(interConf.loginPageRegex())){

            if (conf.username() != null && conf.password() != null) {
                String fillUsername = "document.getElementById('user').value='" + conf.username() + "'";
                String fillPassword = "document.getElementById('password').value='" + conf.password() + "'";
                String sendForm = "document.querySelector('#login_form .btn-login').click()";
                actionable.performAction(e -> {
                    e.executeScript(fillUsername);
                    e.executeScript(fillPassword);
                    e.executeScript(sendForm);
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
