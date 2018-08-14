package cz.valkovic.java.twbot.services.parsers;

import java.net.URI;

public class TWStatsUnitParser extends BaseParser implements Parser {

    @Override
    public boolean willProccess(URI location) {
         return location.getHost().equals("www.twstats.com") &&
                 procesParams(location).size() == 1 &&
                 procesParams(location).get("page").equals("units");
    }

    @Override
    public void proccess(URI location, String content) {

    }
}
