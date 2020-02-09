package cz.valkovic.twbot.services.parsers;

import cz.valkovic.twbot.models.UnitInfo;
import cz.valkovic.twbot.models.UnitTypes;
import cz.valkovic.twbot.models.UnitsSettings;
import cz.valkovic.twbot.services.logging.TestLoggingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TWStatsUnitsParserWithResourcesTest {

    static class ReportClass {
        private UnitsSettings units;

        public void report(UnitsSettings units) {
            this.units = units;
        }
    }

    private static Parser p;
    private static Document d;
    private static ReportClass r = new ReportClass();

    @BeforeEach
    void beforeEach() {
        r.units = null;
    }

    @BeforeAll
    static void beforeAll() throws IOException {
        File toRead = new File(TWStatsUnitsParserWithResourcesTest.class
                .getClassLoader()
                .getResource("twstats_units_withresources.html")
                .getFile()
        );

        d = Jsoup.parse(toRead, "UTF8");

        p = new TWStatsUnitParser(new TestLoggingService());
    }

    @Test
    void shouldParseUnitsPageWithoutError() throws URISyntaxException, MalformedURLException {

        p.proccess(
                new URL("http://www.twstats.com/en9/index.php?page=units"),
                d
        );
    }

    @Test
    void shouldParseSpearFighterUnitWithCorrectValues() throws URISyntaxException, MalformedURLException {

        p.proccess(
                new URL("http://www.twstats.com/en9/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.SpearFighter);
        assertEquals(UnitTypes.SpearFighter, unit.getType());
        assertEquals(50, (int)unit.getWoodCost());
        assertEquals(30, (int)unit.getClayCost());
        assertEquals(10, (int)unit.getIronCost());
        assertEquals(1, unit.getPopulations());
        assertEquals(18, unit.getSpeed());
        assertEquals(10, unit.getAttack());
        assertEquals(15, unit.getDefense());
        assertEquals(45, unit.getDeferenceCavallery());
        assertEquals(20, unit.getDefenseArchers());
        assertEquals(25, unit.getCarry());
        assertEquals(680, unit.getBaseBuildTime());
    }

    @Test
    void shouldParseSwordFighterUnitWithCorrectValues() throws URISyntaxException, MalformedURLException {

        p.proccess(
                new URL("http://www.twstats.com/en9/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.SwordFighter);
        assertEquals(UnitTypes.SwordFighter, unit.getType());
        assertEquals(30, (int)unit.getWoodCost());
        assertEquals(30, (int)unit.getClayCost());
        assertEquals(70, (int)unit.getIronCost());
        assertEquals(1, unit.getPopulations());
        assertEquals(22, unit.getSpeed());
        assertEquals(25, unit.getAttack());
        assertEquals(50, unit.getDefense());
        assertEquals(15, unit.getDeferenceCavallery());
        assertEquals(40, unit.getDefenseArchers());
        assertEquals(15, unit.getCarry());
        assertEquals(1000, unit.getBaseBuildTime());
    }

    @Test
    void shouldParseAxeFighterUnitWithCorrectValues() throws URISyntaxException, MalformedURLException {

        p.proccess(
                new URL("http://www.twstats.com/en9/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.AxeFighter);
        assertEquals(UnitTypes.AxeFighter, unit.getType());
        assertEquals(60, (int)unit.getWoodCost());
        assertEquals(30, (int)unit.getClayCost());
        assertEquals(40, (int)unit.getIronCost());
        assertEquals(1, unit.getPopulations());
        assertEquals(18, unit.getSpeed());
        assertEquals(40, unit.getAttack());
        assertEquals(10, unit.getDefense());
        assertEquals(5, unit.getDeferenceCavallery());
        assertEquals(10, unit.getDefenseArchers());
        assertEquals(10, unit.getCarry());
        assertEquals(880, unit.getBaseBuildTime());
    }

    @Test
    void shouldParseArcherUnitWithCorrectValues() throws URISyntaxException, MalformedURLException {

        p.proccess(
                new URL("http://www.twstats.com/en9/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.Archer);
        assertEquals(UnitTypes.Archer, unit.getType());
        assertEquals(100, (int)unit.getWoodCost());
        assertEquals(30, (int)unit.getClayCost());
        assertEquals(60, (int)unit.getIronCost());
        assertEquals(1, unit.getPopulations());
        assertEquals(18, unit.getSpeed());
        assertEquals(15, unit.getAttack());
        assertEquals(50, unit.getDefense());
        assertEquals(40, unit.getDeferenceCavallery());
        assertEquals(5, unit.getDefenseArchers());
        assertEquals(10, unit.getCarry());
        assertEquals(1200, unit.getBaseBuildTime());
    }

    @Test
    void shouldParseScoutUnitWithCorrectValues() throws URISyntaxException, MalformedURLException {

        p.proccess(
                new URL("http://www.twstats.com/en9/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.Scout);
        assertEquals(UnitTypes.Scout, unit.getType());
        assertEquals(50, (int)unit.getWoodCost());
        assertEquals(50, (int)unit.getClayCost());
        assertEquals(20, (int)unit.getIronCost());
        assertEquals(2, unit.getPopulations());
        assertEquals(9, unit.getSpeed());
        assertEquals(0, unit.getAttack());
        assertEquals(2, unit.getDefense());
        assertEquals(1, unit.getDeferenceCavallery());
        assertEquals(2, unit.getDefenseArchers());
        assertEquals(0, unit.getCarry());
        assertEquals(600, unit.getBaseBuildTime());
    }

    @Test
    void shouldParseLightCavaleryUnitWithCorrectValues() throws URISyntaxException, MalformedURLException {

        p.proccess(
                new URL("http://www.twstats.com/en9/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.LightCavalry);
        assertEquals(UnitTypes.LightCavalry, unit.getType());
        assertEquals(125, (int)unit.getWoodCost());
        assertEquals(100, (int)unit.getClayCost());
        assertEquals(250, (int)unit.getIronCost());
        assertEquals(4, unit.getPopulations());
        assertEquals(10, unit.getSpeed());
        assertEquals(130, unit.getAttack());
        assertEquals(30, unit.getDefense());
        assertEquals(40, unit.getDeferenceCavallery());
        assertEquals(30, unit.getDefenseArchers());
        assertEquals(80, unit.getCarry());
        assertEquals(1200, unit.getBaseBuildTime());
    }

    @Test
    void shouldParseMountedArcherUnitWithCorrectValues() throws URISyntaxException, MalformedURLException {

        p.proccess(
                new URL("http://www.twstats.com/en9/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.MountedArcher);
        assertEquals(UnitTypes.MountedArcher, unit.getType());
        assertEquals(250, (int)unit.getWoodCost());
        assertEquals(100, (int)unit.getClayCost());
        assertEquals(150, (int)unit.getIronCost());
        assertEquals(5, unit.getPopulations());
        assertEquals(10, unit.getSpeed());
        assertEquals(120, unit.getAttack());
        assertEquals(40, unit.getDefense());
        assertEquals(30, unit.getDeferenceCavallery());
        assertEquals(50, unit.getDefenseArchers());
        assertEquals(50, unit.getCarry());
        assertEquals(1800, unit.getBaseBuildTime());
    }

    @Test
    void shouldParseHeavyCavaleryUnitWithCorrectValues() throws URISyntaxException, MalformedURLException {

        p.proccess(
                new URL("http://www.twstats.com/en9/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.HeavyCavalry);
        assertEquals(UnitTypes.HeavyCavalry, unit.getType());
        assertEquals(200, (int)unit.getWoodCost());
        assertEquals(150, (int)unit.getClayCost());
        assertEquals(600, (int)unit.getIronCost());
        assertEquals(6, unit.getPopulations());
        assertEquals(11, unit.getSpeed());
        assertEquals(150, unit.getAttack());
        assertEquals(200, unit.getDefense());
        assertEquals(80, unit.getDeferenceCavallery());
        assertEquals(180, unit.getDefenseArchers());
        assertEquals(50, unit.getCarry());
        assertEquals(2400, unit.getBaseBuildTime());
    }

    @Test
    void shouldParseRamUnitWithCorrectValues() throws URISyntaxException, MalformedURLException {

        p.proccess(
                new URL("http://www.twstats.com/en9/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.Ram);
        assertEquals(UnitTypes.Ram, unit.getType());
        assertEquals(300, (int)unit.getWoodCost());
        assertEquals(200, (int)unit.getClayCost());
        assertEquals(200, (int)unit.getIronCost());
        assertEquals(5, unit.getPopulations());
        assertEquals(30, unit.getSpeed());
        assertEquals(2, unit.getAttack());
        assertEquals(20, unit.getDefense());
        assertEquals(50, unit.getDeferenceCavallery());
        assertEquals(20, unit.getDefenseArchers());
        assertEquals(0, unit.getCarry());
        assertEquals(3200, unit.getBaseBuildTime());
    }

    @Test
    void shouldParseCatapultUnitWithCorrectValues() throws URISyntaxException, MalformedURLException {

        p.proccess(
                new URL("http://www.twstats.com/en9/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.Catapult);
        assertEquals(UnitTypes.Catapult, unit.getType());
        assertEquals(320, (int)unit.getWoodCost());
        assertEquals(400, (int)unit.getClayCost());
        assertEquals(100, (int)unit.getIronCost());
        assertEquals(8, unit.getPopulations());
        assertEquals(30, unit.getSpeed());
        assertEquals(100, unit.getAttack());
        assertEquals(100, unit.getDefense());
        assertEquals(50, unit.getDeferenceCavallery());
        assertEquals(100, unit.getDefenseArchers());
        assertEquals(0, unit.getCarry());
        assertEquals(4800, unit.getBaseBuildTime());
    }

    @Test
    void shouldParseNoblemanUnitWithCorrectValues() throws URISyntaxException, MalformedURLException {

        p.proccess(
                new URL("http://www.twstats.com/en9/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.Nobleman);
        assertEquals(UnitTypes.Nobleman, unit.getType());
        assertEquals(40000, (int)unit.getWoodCost());
        assertEquals(50000, (int)unit.getClayCost());
        assertEquals(50000, (int)unit.getIronCost());
        assertEquals(100, unit.getPopulations());
        assertEquals(35, unit.getSpeed());
        assertEquals(30, unit.getAttack());
        assertEquals(100, unit.getDefense());
        assertEquals(50, unit.getDeferenceCavallery());
        assertEquals(100, unit.getDefenseArchers());
        assertEquals(0, unit.getCarry());
        assertEquals(12000, unit.getBaseBuildTime());
    }

    @Test
    void shouldParsePaladinUnitWithCorrectValues() throws URISyntaxException, MalformedURLException {

        p.proccess(
                new URL("http://www.twstats.com/en9/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.Paladin);
        assertEquals(UnitTypes.Paladin, unit.getType());
        assertEquals(20, (int)unit.getWoodCost());
        assertEquals(20, (int)unit.getClayCost());
        assertEquals(40, (int)unit.getIronCost());
        assertEquals(10, unit.getPopulations());
        assertEquals(10, unit.getSpeed());
        assertEquals(150, unit.getAttack());
        assertEquals(250, unit.getDefense());
        assertEquals(400, unit.getDeferenceCavallery());
        assertEquals(150, unit.getDefenseArchers());
        assertEquals(100, unit.getCarry());
        assertEquals(14400, unit.getBaseBuildTime());
    }
}
