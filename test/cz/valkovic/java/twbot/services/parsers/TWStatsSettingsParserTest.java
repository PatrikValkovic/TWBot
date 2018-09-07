package cz.valkovic.java.twbot.services.parsers;

import cz.valkovic.java.twbot.models.ServerSetting;
import cz.valkovic.java.twbot.services.logging.TestLoggingService;
import cz.valkovic.java.twbot.services.parsers.reporting.ServerSettingsReportingService;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TWStatsSettingsParserTest {

    static class ReportClass implements ServerSettingsReportingService {
        @Getter
        private ServerSetting settings;

        @Override
        public void reportServerSettings(ServerSetting settings) {
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

        p = new TWStatsSettingParser(new TestLoggingService(), r);
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
