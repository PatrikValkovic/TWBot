package cz.valkovic.java.twbot.services.parsers;

import cz.valkovic.java.twbot.exceptions.UnknownUnitException;
import cz.valkovic.java.twbot.models.UnitInfo;
import cz.valkovic.java.twbot.models.UnitTypes;
import cz.valkovic.java.twbot.models.UnitsSettings;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import cz.valkovic.java.twbot.services.parsers.reporting.UnitsSettingsReportingService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.inject.Inject;
import java.net.URL;
import java.util.Arrays;

public class TWStatsUnitParser extends BaseParser implements Parser {

    private LoggingService log;
    private UnitsSettingsReportingService report;

    @Inject
    public TWStatsUnitParser(LoggingService log, UnitsSettingsReportingService report) {
        this.log = log;
        this.report = report;
    }


    @Override
    public boolean willProccess(URL location) {
        return location.getHost().equals("www.twstats.com") &&
                procesParams(location).size() == 1 &&
                procesParams(location).get("page").equals("units");
    }

    @Override
    public void proccess(URL location, Document content) {
        log.getParsing().info("Parsing units for " + location.toString());

        UnitsSettings settings = new UnitsSettings();

        for (Element el : content.select("#main .widget tr")) {

            Elements ths = el.select("td");
            if (ths.size() != 12)
                continue;

            String key = ths.first().text();
            try {
                UnitInfo info = new UnitInfo();

                UnitTypes type = Arrays.stream(UnitTypes.values())
                                       .filter(unitType -> unitType.getName().equals(key))
                                       .findFirst()
                                       .orElseThrow(UnknownUnitException::new);

                Element[] elms = new Element[ths.size()];
                ths.toArray(elms);

                info.setType(type);
                try {
                    info.setWoodCost(Integer.parseUnsignedInt(elms[1].text()));
                    info.setClayCost(Integer.parseUnsignedInt(elms[2].text()));
                    info.setIronCost(Integer.parseUnsignedInt(elms[3].text()));
                }
                catch(NumberFormatException e){
                    log.getParsing().debug("Costs of unit " + key + " is not set");
                }
                info.setPopulations(Integer.parseUnsignedInt(elms[4].text()));
                info.setSpeed(Double.parseDouble(elms[5].text()));
                info.setAttack(Integer.parseUnsignedInt(elms[6].text()));
                info.setDefense(Integer.parseUnsignedInt(elms[7].text()));
                info.setDeferenceCavallery(Integer.parseUnsignedInt(elms[8].text()));
                info.setDefenseArchers(Integer.parseUnsignedInt(elms[9].text()));
                info.setCarry(Integer.parseUnsignedInt(elms[10].text()));
                info.setBaseBuildTime(Double.parseDouble(elms[11].text()));

                settings.getUnits().add(info);
            }
            catch(UnknownUnitException e){
                log.getParsing().debug("Unknown unit =" + el.select("td").first().text() + "=");
            }
            catch (Exception e) {
                log.getParsing().warn("Error parsing unit " + el.select("td").first().text());
                log.getParsing().debug(e, e);
            }
        }

        this.report.reportUnitsSettings(settings);

        log.getParsing().info(location.toString() + " units parsed successfully");
    }
}
