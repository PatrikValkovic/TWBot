package cz.valkovic.twbot.modules.parsing.locations;

import cz.valkovic.twbot.modules.core.pipeping.LocationPipe;
import java.net.URL;
import org.jsoup.nodes.Document;

public class LoginLocPipe implements LocationPipe {

    @Override
    public boolean match(URL url, Document content) {
        return content.getElementById("login_form") != null;
    }
}
