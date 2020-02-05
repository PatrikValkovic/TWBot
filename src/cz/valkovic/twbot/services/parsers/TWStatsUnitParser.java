package cz.valkovic.twbot.services.parsers;

import cz.valkovic.twbot.exceptions.UnknownUnitException;
import cz.valkovic.twbot.models.UnitInfo;
import cz.valkovic.twbot.models.UnitTypes;
import cz.valkovic.twbot.models.UnitsSettings;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.services.parsers.reporting.UnitsSettingsReportingService;
import java.net.URL;
import java.util.Arrays;
import javax.inject.Inject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
                String unit = el.select("td").first().text();
                if(unit.length() == 0)
                    log.getParsing().info("Ignoring empty unit");
                else {
                    log.getParsing().error("Cannot parse unit " + unit);
                    log.getParsing().debug(e, e);
                }
            }
            catch (Exception e) {
                log.getParsing().warn("Error parsing unit " + el.select("td").first().text());
                log.getParsing().debug(e, e);
            }
        }

        this.report.report(settings);

        log.getParsing().info(location.toString() + " units parsed successfully");
    }
}