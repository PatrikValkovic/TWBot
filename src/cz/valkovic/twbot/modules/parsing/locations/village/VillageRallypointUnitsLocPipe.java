package cz.valkovic.twbot.modules.parsing.locations.village;

import cz.valkovic.twbot.modules.core.pipeping.LocationPipe;
import java.net.URL;
import java.util.Map;
import org.jsoup.nodes.Document;

public class VillageRallypointUnitsLocPipe implements LocationPipe {

    @Override
    public boolean match(URL url, Document content) {
        Map<String, String> p = this.getParameters(url);
        return p.getOrDefault("screen", "").equals("place") &&
                p.getOrDefault("mode", "").equals("units") &&
                p.getOrDefault("display", "units").equals("units");
    }
}
