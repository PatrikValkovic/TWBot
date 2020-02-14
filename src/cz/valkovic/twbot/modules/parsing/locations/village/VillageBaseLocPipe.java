package cz.valkovic.twbot.modules.parsing.locations.village;

import cz.valkovic.twbot.modules.core.pipeping.LocationPipe;
import java.net.URL;
import org.jsoup.nodes.Document;

public interface VillageBaseLocPipe extends LocationPipe {

    String getScreenKey();

    @Override
    default boolean match(URL url, Document content) {
        return this.getParameters(url).getOrDefault("screen", "").equals(this.getScreenKey());
    }
}
