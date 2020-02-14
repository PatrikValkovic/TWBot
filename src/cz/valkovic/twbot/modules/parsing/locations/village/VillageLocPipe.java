package cz.valkovic.twbot.modules.parsing.locations.village;

import cz.valkovic.twbot.modules.core.pipeping.LocationPipe;
import java.net.URL;
import org.jsoup.nodes.Document;

public class VillageLocPipe implements LocationPipe {

    @Override
    public boolean match(URL url, Document content) {
        return true;
    }
}
