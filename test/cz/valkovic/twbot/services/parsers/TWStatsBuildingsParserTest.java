package cz.valkovic.twbot.services.parsers;

import cz.valkovic.twbot.models.BuildingInfo;
import cz.valkovic.twbot.models.BuildingSettings;
import cz.valkovic.twbot.models.BuildingTypes;
import cz.valkovic.twbot.services.logging.TestLoggingService;
import cz.valkovic.twbot.services.parsers.reporting.BuildingSettingsReportingService;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TWStatsBuildingsParserTest {

    static class ReportClass implements BuildingSettingsReportingService {
        private BuildingSettings buildings;


        @Override
        public void report(BuildingSettings units) {
            this.buildings = units;
        }
    }

    private static Parser p;
    private static Document d;
    private static ReportClass r = new ReportClass();

    @BeforeEach
    void beforeEach() {
        r.buildings = null;
    }

    @BeforeAll
    static void beforeAll() throws IOException {
        File toRead = new File(TWStatsBuildingsParserTest.class
                .getClassLoader()
                .getResource("twstats_buildings.html")
                .getFile()
        );

        d = Jsoup.parse(toRead, "UTF8");

        p = new TWStatsBuildingsParser(new TestLoggingService(), r);
    }

    @Test
    void shouldParseBuildingsPageWithoutError() throws MalformedURLException {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=buildings"),
                d
        );
    }

    @Test
    void shouldParseHeadquarter() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=buildings"),
                d
        );

        BuildingInfo building = r.buildings.getInfo(BuildingTypes.Headquarters);
        assertEquals(BuildingTypes.Headquarters, building.getType());
        assertEquals(30, building.getMaxLevel());
        assertEquals(1, building.getMinLevel());
        assertEquals(90, building.getWood());
        assertEquals(80, building.getClay());
        assertEquals(70, building.getIron());
        assertEquals(5, building.getPop());
        assertEquals(1.26, building.getWoodFactor());
        assertEquals(1.275, building.getClayFactor());
        assertEquals(1.26, building.getIronFactor());
        assertEquals(1.17, building.getPopFactor());
        assertEquals(8*60+49, building.getBaseBuildTime());
        assertEquals(1.2, building.getBuildTimeFactor());
    }

    @Test
    void shouldParseBarracks() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=buildings"),
                d
        );

        BuildingInfo building = r.buildings.getInfo(BuildingTypes.Barracks);
        assertEquals(BuildingTypes.Barracks, building.getType());
        assertEquals(25, building.getMaxLevel());
        assertEquals(0, building.getMinLevel());
        assertEquals(200, building.getWood());
        assertEquals(170, building.getClay());
        assertEquals(90, building.getIron());
        assertEquals(7, building.getPop());
        assertEquals(1.26, building.getWoodFactor());
        assertEquals(1.28, building.getClayFactor());
        assertEquals(1.26, building.getIronFactor());
        assertEquals(1.17, building.getPopFactor());
        assertEquals(17*60+38, building.getBaseBuildTime());
        assertEquals(1.2, building.getBuildTimeFactor());
    }

    @Test
    void shouldParseStable() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=buildings"),
                d
        );

        BuildingInfo building = r.buildings.getInfo(BuildingTypes.Stable);
        assertEquals(BuildingTypes.Stable, building.getType());
        assertEquals(20, building.getMaxLevel());
        assertEquals(0, building.getMinLevel());
        assertEquals(270, building.getWood());
        assertEquals(240, building.getClay());
        assertEquals(260, building.getIron());
        assertEquals(8, building.getPop());
        assertEquals(1.26, building.getWoodFactor());
        assertEquals(1.28, building.getClayFactor());
        assertEquals(1.26, building.getIronFactor());
        assertEquals(1.17, building.getPopFactor());
        assertEquals(58*60+49, building.getBaseBuildTime());
        assertEquals(1.2, building.getBuildTimeFactor());
    }

    @Test
    void shouldParseWorkshop() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=buildings"),
                d
        );

        BuildingInfo building = r.buildings.getInfo(BuildingTypes.Workshop);
        assertEquals(BuildingTypes.Workshop, building.getType());
        assertEquals(15, building.getMaxLevel());
        assertEquals(0, building.getMinLevel());
        assertEquals(300, building.getWood());
        assertEquals(240, building.getClay());
        assertEquals(260, building.getIron());
        assertEquals(8, building.getPop());
        assertEquals(1.26, building.getWoodFactor());
        assertEquals(1.28, building.getClayFactor());
        assertEquals(1.26, building.getIronFactor());
        assertEquals(1.17, building.getPopFactor());
        assertEquals(58*60+49, building.getBaseBuildTime());
        assertEquals(1.2, building.getBuildTimeFactor());
    }

    @Test
    void shouldParseAcademy() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=buildings"),
                d
        );

        BuildingInfo building = r.buildings.getInfo(BuildingTypes.Academy);
        assertEquals(BuildingTypes.Academy, building.getType());
        assertEquals(1, building.getMaxLevel());
        assertEquals(0, building.getMinLevel());
        assertEquals(15000, building.getWood());
        assertEquals(25000, building.getClay());
        assertEquals(10000, building.getIron());
        assertEquals(80, building.getPop());
        assertEquals(2, building.getWoodFactor());
        assertEquals(2, building.getClayFactor());
        assertEquals(2, building.getIronFactor());
        assertEquals(1.17, building.getPopFactor());
        assertEquals(5752*60+56, building.getBaseBuildTime());
        assertEquals(1.2, building.getBuildTimeFactor());
    }

    @Test
    void shouldParseSmithy() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=buildings"),
                d
        );

        BuildingInfo building = r.buildings.getInfo(BuildingTypes.Smithy);
        assertEquals(BuildingTypes.Smithy, building.getType());
        assertEquals(20, building.getMaxLevel());
        assertEquals(0, building.getMinLevel());
        assertEquals(220, building.getWood());
        assertEquals(180, building.getClay());
        assertEquals(240, building.getIron());
        assertEquals(20, building.getPop());
        assertEquals(1.26, building.getWoodFactor());
        assertEquals(1.275, building.getClayFactor());
        assertEquals(1.26, building.getIronFactor());
        assertEquals(1.17, building.getPopFactor());
        assertEquals(58*60+49, building.getBaseBuildTime());
        assertEquals(1.2, building.getBuildTimeFactor());
    }

    @Test
    void shouldParseRallyPoint() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=buildings"),
                d
        );

        BuildingInfo building = r.buildings.getInfo(BuildingTypes.RallyPoint);
        assertEquals(BuildingTypes.RallyPoint, building.getType());
        assertEquals(1, building.getMaxLevel());
        assertEquals(0, building.getMinLevel());
        assertEquals(10, building.getWood());
        assertEquals(40, building.getClay());
        assertEquals(30, building.getIron());
        assertEquals(0, building.getPop());
        assertEquals(1.26, building.getWoodFactor());
        assertEquals(1.275, building.getClayFactor());
        assertEquals(1.26, building.getIronFactor());
        assertEquals(1.17, building.getPopFactor());
        assertEquals(106*60+28, building.getBaseBuildTime());
        assertEquals(1.2, building.getBuildTimeFactor());
    }

    @Test
    void shouldParseMarket() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=buildings"),
                d
        );

        BuildingInfo building = r.buildings.getInfo(BuildingTypes.Market);
        assertEquals(BuildingTypes.Market, building.getType());
        assertEquals(25, building.getMaxLevel());
        assertEquals(0, building.getMinLevel());
        assertEquals(100, building.getWood());
        assertEquals(100, building.getClay());
        assertEquals(100, building.getIron());
        assertEquals(20, building.getPop());
        assertEquals(1.26, building.getWoodFactor());
        assertEquals(1.275, building.getClayFactor());
        assertEquals(1.26, building.getIronFactor());
        assertEquals(1.17, building.getPopFactor());
        assertEquals(26*60+28, building.getBaseBuildTime());
        assertEquals(1.2, building.getBuildTimeFactor());
    }

    @Test
    void shouldParseTimberCamp() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=buildings"),
                d
        );

        BuildingInfo building = r.buildings.getInfo(BuildingTypes.TimberCamp);
        assertEquals(BuildingTypes.TimberCamp, building.getType());
        assertEquals(30, building.getMaxLevel());
        assertEquals(0, building.getMinLevel());
        assertEquals(50, building.getWood());
        assertEquals(60, building.getClay());
        assertEquals(40, building.getIron());
        assertEquals(5, building.getPop());
        assertEquals(1.25, building.getWoodFactor());
        assertEquals(1.275, building.getClayFactor());
        assertEquals(1.245, building.getIronFactor());
        assertEquals(1.155, building.getPopFactor());
        assertEquals(8*60+49, building.getBaseBuildTime());
        assertEquals(1.2, building.getBuildTimeFactor());
    }

    @Test
    void shouldParseClayPit() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=buildings"),
                d
        );

        BuildingInfo building = r.buildings.getInfo(BuildingTypes.ClayPit);
        assertEquals(BuildingTypes.ClayPit, building.getType());
        assertEquals(30, building.getMaxLevel());
        assertEquals(0, building.getMinLevel());
        assertEquals(65, building.getWood());
        assertEquals(50, building.getClay());
        assertEquals(40, building.getIron());
        assertEquals(10, building.getPop());
        assertEquals(1.27, building.getWoodFactor());
        assertEquals(1.265, building.getClayFactor());
        assertEquals(1.24, building.getIronFactor());
        assertEquals(1.14, building.getPopFactor());
        assertEquals(8*60+49, building.getBaseBuildTime());
        assertEquals(1.2, building.getBuildTimeFactor());
    }

    @Test
    void shouldParseIronMine() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=buildings"),
                d
        );

        BuildingInfo building = r.buildings.getInfo(BuildingTypes.IronMine);
        assertEquals(BuildingTypes.IronMine, building.getType());
        assertEquals(30, building.getMaxLevel());
        assertEquals(0, building.getMinLevel());
        assertEquals(75, building.getWood());
        assertEquals(65, building.getClay());
        assertEquals(70, building.getIron());
        assertEquals(10, building.getPop());
        assertEquals(1.252, building.getWoodFactor());
        assertEquals(1.275, building.getClayFactor());
        assertEquals(1.24, building.getIronFactor());
        assertEquals(1.17, building.getPopFactor());
        assertEquals(10*60+35, building.getBaseBuildTime());
        assertEquals(1.2, building.getBuildTimeFactor());
    }

    @Test
    void shouldParseFarm() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=buildings"),
                d
        );

        BuildingInfo building = r.buildings.getInfo(BuildingTypes.Farm);
        assertEquals(BuildingTypes.Farm, building.getType());
        assertEquals(30, building.getMaxLevel());
        assertEquals(1, building.getMinLevel());
        assertEquals(45, building.getWood());
        assertEquals(40, building.getClay());
        assertEquals(30, building.getIron());
        assertEquals(0, building.getPop());
        assertEquals(1.3, building.getWoodFactor());
        assertEquals(1.32, building.getClayFactor());
        assertEquals(1.29, building.getIronFactor());
        assertEquals(1, building.getPopFactor());
        assertEquals(11*60+45, building.getBaseBuildTime());
        assertEquals(1.2, building.getBuildTimeFactor());
    }

    @Test
    void shouldParseWarehouse() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=buildings"),
                d
        );

        BuildingInfo building = r.buildings.getInfo(BuildingTypes.Warehouse);
        assertEquals(BuildingTypes.Warehouse, building.getType());
        assertEquals(30, building.getMaxLevel());
        assertEquals(1, building.getMinLevel());
        assertEquals(60, building.getWood());
        assertEquals(50, building.getClay());
        assertEquals(40, building.getIron());
        assertEquals(0, building.getPop());
        assertEquals(1.265, building.getWoodFactor());
        assertEquals(1.27, building.getClayFactor());
        assertEquals(1.245, building.getIronFactor());
        assertEquals(1.15, building.getPopFactor());
        assertEquals(10*60+0, building.getBaseBuildTime());
        assertEquals(1.2, building.getBuildTimeFactor());
    }

    @Test
    void shouldParseHidingPlace() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=buildings"),
                d
        );

        BuildingInfo building = r.buildings.getInfo(BuildingTypes.HidingPlace);
        assertEquals(BuildingTypes.HidingPlace, building.getType());
        assertEquals(10, building.getMaxLevel());
        assertEquals(0, building.getMinLevel());
        assertEquals(50, building.getWood());
        assertEquals(60, building.getClay());
        assertEquals(50, building.getIron());
        assertEquals(2, building.getPop());
        assertEquals(1.25, building.getWoodFactor());
        assertEquals(1.25, building.getClayFactor());
        assertEquals(1.25, building.getIronFactor());
        assertEquals(1.17, building.getPopFactor());
        assertEquals(17*60+38, building.getBaseBuildTime());
        assertEquals(1.2, building.getBuildTimeFactor());
    }

    @Test
    void shouldParseWall() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=buildings"),
                d
        );

        BuildingInfo building = r.buildings.getInfo(BuildingTypes.Wall);
        assertEquals(BuildingTypes.Wall, building.getType());
        assertEquals(20, building.getMaxLevel());
        assertEquals(0, building.getMinLevel());
        assertEquals(50, building.getWood());
        assertEquals(100, building.getClay());
        assertEquals(20, building.getIron());
        assertEquals(5, building.getPop());
        assertEquals(1.26, building.getWoodFactor());
        assertEquals(1.275, building.getClayFactor());
        assertEquals(1.26, building.getIronFactor());
        assertEquals(1.17, building.getPopFactor());
        assertEquals(35*60+17, building.getBaseBuildTime());
        assertEquals(1.2, building.getBuildTimeFactor());
    }






}
