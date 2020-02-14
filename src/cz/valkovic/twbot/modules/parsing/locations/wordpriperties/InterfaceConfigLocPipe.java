package cz.valkovic.twbot.modules.parsing.locations.wordpriperties;

import cz.valkovic.twbot.modules.core.pipeping.LocationPipe;
import java.net.URL;
import org.jsoup.nodes.Document;

public class InterfaceConfigLocPipe implements LocationPipe {

    @Override
    public boolean match(URL url, Document content) {
        return getParameters(url).getOrDefault("func", "").equals("get_config");
    }
}
