package cz.valkovic.twbot.modules.parsing.locations.village;

import cz.valkovic.twbot.modules.core.pipeping.LocationPipe;
import java.net.URL;
import java.util.Map;
import org.jsoup.nodes.Document;

public class VillageMarketOffersLocPipe implements LocationPipe {

    @Override
    public boolean match(URL url, Document content) {
        Map<String, String> p = this.getParameters(url);
        return p.getOrDefault("screen", "").equals("market") &&
                p.getOrDefault("mode", "").equals("all_own_offer");
    }
}
