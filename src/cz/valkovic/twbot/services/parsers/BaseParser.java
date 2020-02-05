package cz.valkovic.twbot.services.parsers;

import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

class BaseParser {

    protected Map<String, String> procesParams(URL uri) {
        Map<String, String> dict = new TreeMap<>();
        for (String params : uri.getQuery().split("[?]")) {
            String[] p = params.split("=", 2);
            dict.put(p[0], p.length > 1 ? p[1] : "");
        }
        return dict;
    }

}
