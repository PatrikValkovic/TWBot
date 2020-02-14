package cz.valkovic.twbot.modules.parsing.locations.stats;

import cz.valkovic.twbot.modules.core.pipeping.LocationPipe;
import java.net.URL;
import org.jsoup.nodes.Document;

public class StatsPlayersLocPipe implements LocationPipe {

    @Override
    public boolean match(URL url, Document content) {
        return url.getFile().endsWith("/player.txt");
    }
}
