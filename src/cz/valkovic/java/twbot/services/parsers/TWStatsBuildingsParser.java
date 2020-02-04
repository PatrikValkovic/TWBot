package cz.valkovic.java.twbot.services.parsers;

import cz.valkovic.java.twbot.exceptions.UnknownBuildingException;
import cz.valkovic.java.twbot.models.BuildingInfo;
import cz.valkovic.java.twbot.models.BuildingSettings;
import cz.valkovic.java.twbot.models.BuildingTypes;
import cz.valkovic.java.twbot.modules.core.logging.LoggingService;
import cz.valkovic.java.twbot.services.parsers.reporting.BuildingSettingsReportingService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.inject.Inject;
import java.net.URL;
import java.util.Arrays;

public class TWStatsBuildingsParser extends BaseParser implements Parser {

    private LoggingService log;
    private BuildingSettingsReportingService report;

    @Inject
    public TWStatsBuildingsParser(LoggingService log, BuildingSettingsReportingService report) {
        this.log = log;
        this.report = report;
    }


    @Override
    public boolean willProccess(URL location) {
        return location.getHost().equals("www.twstats.com") &&
                procesParams(location).size() == 1 &&
                procesParams(location).get("page").equals("buildings");
    }

    @Override
    public void proccess(URL location, Document content) {
        log.getParsing().info("Parsing buildings for " + location.toString());

        BuildingSettings settings = new BuildingSettings();

        for (Element el : content.select("#main .widget tr")) {

            Elements ths = el.select("td");
            if (ths.size() == 0)
                continue;

            if(ths.size() != 13){
                log.getParsing().error("Unexpected amount of cells");
                return;
            }

            String key = ths.first().text();
            try {
                BuildingInfo info = new BuildingInfo();

                BuildingTypes type = Arrays.stream(BuildingTypes.values())
                                           .filter(buildingType -> buildingType.getName().equals(key))
                                           .findFirst()
                                           .orElseThrow(UnknownBuildingException::new);

                Element[] elms = new Element[ths.size()];
                ths.toArray(elms);

                info.setType(type);
                info.setMaxLevel(Integer.parseUnsignedInt(elms[1].text()));
                info.setMinLevel(Integer.parseUnsignedInt(elms[2].text()));
                info.setWood(Integer.parseUnsignedInt(elms[3].text()));
                info.setClay(Integer.parseUnsignedInt(elms[4].text()));
                info.setIron(Integer.parseUnsignedInt(elms[5].text()));
                info.setPop(Integer.parseUnsignedInt(elms[6].text()));
                info.setWoodFactor(Double.parseDouble(elms[7].text()));
                info.setClayFactor(Double.parseDouble(elms[8].text()));
                info.setIronFactor(Double.parseDouble(elms[9].text()));
                info.setPopFactor(Double.parseDouble(elms[10].text()));

                //parse base build time
                String buildTime = elms[11].text();
                String[] splittedTime = buildTime.split(":");
                int minues = Integer.parseUnsignedInt(splittedTime[0]);
                int seconds = Integer.parseUnsignedInt(splittedTime[1]);
                info.setBaseBuildTime(minues * 60 + seconds);

                info.setBuildTimeFactor(Double.parseDouble(elms[12].text()));

                settings.getBuildings().add(info);
            }
            catch(UnknownBuildingException e){
                String building = el.select("td").first().text();
                if(building.length() == 0)
                    log.getParsing().info("Ignoring empty building");
                else {
                    log.getParsing().error("Cannot parse building " + building);
                    log.getParsing().debug(e, e);
                }
            }
            catch (Exception e) {
                log.getParsing().warn("Error parsing building " + el.select("td").first().text());
                log.getParsing().debug(e, e);
            }
        }

        this.report.report(settings);

        log.getParsing().info(location.toString() + " buildings parsed successfully");
    }
}
