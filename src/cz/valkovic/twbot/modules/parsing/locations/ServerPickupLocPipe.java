package cz.valkovic.twbot.modules.parsing.locations;

import cz.valkovic.twbot.modules.core.pipeping.LocationPipe;
import java.net.URL;
import org.jsoup.nodes.Document;

public class ServerPickupLocPipe implements LocationPipe {

    @Override
    public boolean match(URL url, Document content) {
        return content.getElementsByClass("worlds-container").size() > 0;
    }
}
