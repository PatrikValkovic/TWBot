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
import java.net.URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TWStatsUnitsParserTest {

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
        File toRead = new File(TWStatsUnitsParserTest.class
                .getClassLoader()
                .getResource("twstats_units.html")
                .getFile()
        );

        d = Jsoup.parse(toRead, "UTF8");

        p = new TWStatsUnitParser(new TestLoggingService());
    }

    @Test
    void shouldParseUnitsPageWithoutError() throws MalformedURLException {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=units"),
                d
        );
    }

    @Test
    void shouldParseSpearFighterUnitWithCorrectValues() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=units"),
                d
        );

        UnitInfo spearFighter = r.units.getInfo(UnitTypes.SpearFighter);
        assertEquals(UnitTypes.SpearFighter, spearFighter.getType());
        assertNull(spearFighter.getWoodCost());
        assertNull(spearFighter.getClayCost());
        assertNull(spearFighter.getIronCost());
        assertEquals(1, spearFighter.getPopulations());
        assertEquals(17.647, spearFighter.getSpeed());
        assertEquals(10, spearFighter.getAttack());
        assertEquals(15, spearFighter.getDefense());
        assertEquals(45, spearFighter.getDeferenceCavallery());
        assertEquals(20, spearFighter.getDefenseArchers());
        assertEquals(25, spearFighter.getCarry());
        assertEquals(600, spearFighter.getBaseBuildTime());
    }

    @Test
    void shouldParseSwordFighterUnitWithCorrectValues() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=units"),
                d
        );

        UnitInfo swordFighter = r.units.getInfo(UnitTypes.SwordFighter);
        assertEquals(UnitTypes.SwordFighter, swordFighter.getType());
        assertNull(swordFighter.getWoodCost());
        assertNull(swordFighter.getClayCost());
        assertNull(swordFighter.getIronCost());
        assertEquals(1, swordFighter.getPopulations());
        assertEquals(21.569, swordFighter.getSpeed());
        assertEquals(25, swordFighter.getAttack());
        assertEquals(50, swordFighter.getDefense());
        assertEquals(15, swordFighter.getDeferenceCavallery());
        assertEquals(40, swordFighter.getDefenseArchers());
        assertEquals(15, swordFighter.getCarry());
        assertEquals(882.3529412, swordFighter.getBaseBuildTime());
    }

    @Test
    void shouldParseAxeFighterUnitWithCorrectValues() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=units"),
                d
        );

        UnitInfo axeFighter = r.units.getInfo(UnitTypes.AxeFighter);
        assertEquals(UnitTypes.AxeFighter, axeFighter.getType());
        assertNull(axeFighter.getWoodCost());
        assertNull(axeFighter.getClayCost());
        assertNull(axeFighter.getIronCost());
        assertEquals(1, axeFighter.getPopulations());
        assertEquals(17.647, axeFighter.getSpeed());
        assertEquals(40, axeFighter.getAttack());
        assertEquals(10, axeFighter.getDefense());
        assertEquals(5, axeFighter.getDeferenceCavallery());
        assertEquals(10, axeFighter.getDefenseArchers());
        assertEquals(10, axeFighter.getCarry());
        assertEquals(776.4705882, axeFighter.getBaseBuildTime());
    }

    @Test
    void shouldParseArcherUnitWithCorrectValues() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.Archer);
        assertEquals(UnitTypes.Archer, unit.getType());
        assertNull(unit.getWoodCost());
        assertNull(unit.getClayCost());
        assertNull(unit.getIronCost());
        assertEquals(1, unit.getPopulations());
        assertEquals(17.647, unit.getSpeed());
        assertEquals(15, unit.getAttack());
        assertEquals(50, unit.getDefense());
        assertEquals(40, unit.getDeferenceCavallery());
        assertEquals(5, unit.getDefenseArchers());
        assertEquals(10, unit.getCarry());
        assertEquals(1058.823529, unit.getBaseBuildTime());
    }

    @Test
    void shouldParseScoutUnitWithCorrectValues() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.Scout);
        assertEquals(UnitTypes.Scout, unit.getType());
        assertNull(unit.getWoodCost());
        assertNull(unit.getClayCost());
        assertNull(unit.getIronCost());
        assertEquals(2, unit.getPopulations());
        assertEquals(8.824, unit.getSpeed());
        assertEquals(0, unit.getAttack());
        assertEquals(2, unit.getDefense());
        assertEquals(1, unit.getDeferenceCavallery());
        assertEquals(2, unit.getDefenseArchers());
        assertEquals(0, unit.getCarry());
        assertEquals(529.4117647, unit.getBaseBuildTime());
    }

    @Test
    void shouldParseLightCavaleryUnitWithCorrectValues() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.LightCavalry);
        assertEquals(UnitTypes.LightCavalry, unit.getType());
        assertNull(unit.getWoodCost());
        assertNull(unit.getClayCost());
        assertNull(unit.getIronCost());
        assertEquals(4, unit.getPopulations());
        assertEquals(9.804, unit.getSpeed());
        assertEquals(130, unit.getAttack());
        assertEquals(30, unit.getDefense());
        assertEquals(40, unit.getDeferenceCavallery());
        assertEquals(30, unit.getDefenseArchers());
        assertEquals(80, unit.getCarry());
        assertEquals(1058.823529, unit.getBaseBuildTime());
    }

    @Test
    void shouldParseMountedArcherUnitWithCorrectValues() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.MountedArcher);
        assertEquals(UnitTypes.MountedArcher, unit.getType());
        assertNull(unit.getWoodCost());
        assertNull(unit.getClayCost());
        assertNull(unit.getIronCost());
        assertEquals(5, unit.getPopulations());
        assertEquals(9.804, unit.getSpeed());
        assertEquals(120, unit.getAttack());
        assertEquals(40, unit.getDefense());
        assertEquals(30, unit.getDeferenceCavallery());
        assertEquals(50, unit.getDefenseArchers());
        assertEquals(50, unit.getCarry());
        assertEquals(1588.235294, unit.getBaseBuildTime());
    }

    @Test
    void shouldParseHeavyCavaleryUnitWithCorrectValues() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.HeavyCavalry);
        assertEquals(UnitTypes.HeavyCavalry, unit.getType());
        assertNull(unit.getWoodCost());
        assertNull(unit.getClayCost());
        assertNull(unit.getIronCost());
        assertEquals(6, unit.getPopulations());
        assertEquals(10.784, unit.getSpeed());
        assertEquals(150, unit.getAttack());
        assertEquals(200, unit.getDefense());
        assertEquals(80, unit.getDeferenceCavallery());
        assertEquals(180, unit.getDefenseArchers());
        assertEquals(50, unit.getCarry());
        assertEquals(2117.647059, unit.getBaseBuildTime());
    }

    @Test
    void shouldParseRamUnitWithCorrectValues() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.Ram);
        assertEquals(UnitTypes.Ram, unit.getType());
        assertNull(unit.getWoodCost());
        assertNull(unit.getClayCost());
        assertNull(unit.getIronCost());
        assertEquals(5, unit.getPopulations());
        assertEquals(29.412, unit.getSpeed());
        assertEquals(2, unit.getAttack());
        assertEquals(20, unit.getDefense());
        assertEquals(50, unit.getDeferenceCavallery());
        assertEquals(20, unit.getDefenseArchers());
        assertEquals(0, unit.getCarry());
        assertEquals(2823.529412, unit.getBaseBuildTime());
    }

    @Test
    void shouldParseCatapultUnitWithCorrectValues() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.Catapult);
        assertEquals(UnitTypes.Catapult, unit.getType());
        assertNull(unit.getWoodCost());
        assertNull(unit.getClayCost());
        assertNull(unit.getIronCost());
        assertEquals(8, unit.getPopulations());
        assertEquals(29.412, unit.getSpeed());
        assertEquals(100, unit.getAttack());
        assertEquals(100, unit.getDefense());
        assertEquals(50, unit.getDeferenceCavallery());
        assertEquals(100, unit.getDefenseArchers());
        assertEquals(0, unit.getCarry());
        assertEquals(4235.294118, unit.getBaseBuildTime());
    }

    @Test
    void shouldParseNoblemanUnitWithCorrectValues() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.Nobleman);
        assertEquals(UnitTypes.Nobleman, unit.getType());
        assertNull(unit.getWoodCost());
        assertNull(unit.getClayCost());
        assertNull(unit.getIronCost());
        assertEquals(100, unit.getPopulations());
        assertEquals(34.314, unit.getSpeed());
        assertEquals(30, unit.getAttack());
        assertEquals(100, unit.getDefense());
        assertEquals(50, unit.getDeferenceCavallery());
        assertEquals(100, unit.getDefenseArchers());
        assertEquals(0, unit.getCarry());
        assertEquals(10588.23529, unit.getBaseBuildTime());
    }

    @Test
    void shouldReturnNullForPaladin() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=units"),
                d
        );

        UnitInfo unit = r.units.getInfo(UnitTypes.Paladin);
        assertNull(unit);
    }
}
