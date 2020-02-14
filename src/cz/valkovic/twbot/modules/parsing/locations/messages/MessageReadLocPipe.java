package cz.valkovic.twbot.modules.parsing.locations.messages;

import cz.valkovic.twbot.modules.core.pipeping.LocationPipe;
import java.net.URL;
import java.util.Map;
import org.jsoup.nodes.Document;

public class MessageReadLocPipe implements LocationPipe {

    @Override
    public boolean match(URL url, Document content) {
        Map<String, String> m = this.getParameters(url);
        return m.getOrDefault("screen", "").equals("mail") &&
                m.getOrDefault("mode", "").equals("view");
    }
}
