package cz.valkovic.twbot.services.parsers;

import cz.valkovic.twbot.models.ServerSetting;
import fakes.FakeLoggingService;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.net.URL;

class TWStatsSettingsParserTest {

    static class ReportClass {
        @Getter
        private ServerSetting settings;

        public void report(ServerSetting settings) {
            this.settings = settings;
        }
    }

    private static Parser p;
    private static Document d;
    private static ReportClass r = new ReportClass();

    @BeforeEach
    void beforeEach() {
        r.settings = null;
    }

    @BeforeAll
    static void beforeAll() throws IOException {
        File toRead = new File(TWStatsSettingsParserTest.class
                .getClassLoader()
                .getResource("twstats_settings.html")
                .getFile()
        );

        d = Jsoup.parse(toRead, "UTF8");

        p = new TWStatsSettingParser(new FakeLoggingService());
    }

    @Test
    void shouldParseSettingsPageWithoutError() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=settings"),
                d
        );
    }

    @Test
    void shouldParseSettingsPageWithCorrectValues() throws Exception {

        p.proccess(
                new URL("http://www.twstats.com/cs57/index.php?page=settings"),
                d
        );

        assertEquals(1.7, r.getSettings().getSpeed());
        assertEquals(0.6, r.getSettings().getUnitSpeedModifier());
        assertEquals(true, r.getSettings().isMoralActivated());
        assertEquals(true, r.getSettings().isBuildingDestruction());
        assertEquals(300, r.getSettings().getTradeCancelTime());
        assertEquals(600, r.getSettings().getCommandCancelTime());
        assertEquals(3, r.getSettings().getBeginerProtection());
        assertEquals(false, r.getSettings().isPaladinActivated());
        assertEquals(true, r.getSettings().isArchersActivated());
        assertEquals(true, r.getSettings().isGoldCoinSystem());
        assertEquals(100, r.getSettings().getMaxNobleDistance());
        assertEquals(30, r.getSettings().getTribeMemberLimit());

    }


}
