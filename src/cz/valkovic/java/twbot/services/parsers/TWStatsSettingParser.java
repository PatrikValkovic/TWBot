package cz.valkovic.java.twbot.services.parsers;

import cz.valkovic.java.twbot.models.ServerSetting;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.inject.Inject;
import java.net.URI;

public class TWStatsSettingParser extends BaseParser implements Parser {


    @Inject
    public TWStatsSettingParser(LoggingService log) {
        this.log = log;
    }

    private LoggingService log;

    @Override
    public boolean willProccess(URI location) {
        return location.getHost().equals("www.twstats.com") &&
                procesParams(location).size() == 1 &&
                procesParams(location).get("page").equals("settings");
    }

    @Override
    public void proccess(URI location, Document content) {
        log.getParsing().info("Parsing settings for " + location.toString());

        ServerSetting settings = new ServerSetting();

        for (Element el : content.select("#main .widget tr")) {

            Elements ths = el.select("td");
            if(ths.size() != 2)
                continue;

            String key = ths.first().text();
            String value = ths.last().text();
            try {
                switch (key) {
                    case "Speed":
                        settings.setSpeed(Double.parseDouble(value));
                        break;
                    case "Unit speed modifier":
                        settings.setUnitSpeedModifier(Double.parseDouble(value));
                        break;
                    case "Moral activated":
                        settings.setMoralActivated(value.equals("Yes"));
                        break;
                    case "Building destruction activated":
                        settings.setBuildingDestruction(value.equals("Yes"));
                        break;
                    case "Trade Cancel time (secs)":
                        settings.setTradeCancelTime(Integer.parseUnsignedInt(value));
                        break;
                    case "Command Cancel time (secs)":
                        settings.setCommandCancelTime(Integer.parseUnsignedInt(value));
                        break;
                    case "Beginner protection (days)":
                        settings.setBeginerProtection(Integer.parseUnsignedInt(value));
                        break;
                    case "Paladin Activated":
                        settings.setPaladinActivated(value.equals("Yes"));
                        break;
                    case "Archers Activated":
                        settings.setArchersActivated(value.equals("Yes"));
                        break;
                    case "Gold Coin system":
                        settings.setGoldCoinSystem(value.equals("Yes"));
                        break;
                    case "Max noble distance":
                        settings.setMaxNobleDistance(Integer.parseUnsignedInt(value));
                        break;
                    case "Tribe member limit":
                        settings.setTribeMemberLimit(Integer.parseUnsignedInt(value));
                        break;
                }
            }
            catch (Exception e) {
                log.getParsing().warn("Error parsing server setting property " + el.select("th").first().text());
            }
        }

        log.getParsing().info(location.toString() + " parsed successfully");
    }
}
