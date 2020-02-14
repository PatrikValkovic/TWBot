package cz.valkovic.twbot.modules.parsing.locations.reports;

import cz.valkovic.twbot.modules.core.pipeping.LocationPipe;
import org.jsoup.nodes.Document;
import java.net.URL;
import java.util.Map;

public class ReportReadLocPipe implements LocationPipe {

    @Override
    public boolean match(URL url, Document content) {
        Map<String, String> p = this.getParameters(url);
        return p.getOrDefault("screen", "").equals("report") &&
                p.get("view") != null;
    }
}
