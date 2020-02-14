package cz.valkovic.twbot.modules.parsing.locations.tribe;

import cz.valkovic.twbot.modules.core.pipeping.LocationPipe;
import org.jsoup.nodes.Document;
import java.net.URL;

public class WarsLocPipe implements LocationPipe {

    @Override
    public boolean match(URL url, Document content) {
        return this.getParameters(url).getOrDefault("screen", "").equals("wars");
    }
}
