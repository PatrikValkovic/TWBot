package cz.valkovic.twbot.modules.parsing.locations.tribe;

import cz.valkovic.twbot.modules.core.pipeping.LocationPipe;
import org.jsoup.nodes.Document;
import java.net.URL;
import java.util.Map;

public class ForumThreadLocPipe implements LocationPipe {

    @Override
    public boolean match(URL url, Document content) {
        Map<String, String> p = this.getParameters(url);
        return p.getOrDefault("screen", "").equals("forum") &&
                p.getOrDefault("screenmode", "").equals("view_thread") &&
                p.get("thread_id") != null;
    }
}
