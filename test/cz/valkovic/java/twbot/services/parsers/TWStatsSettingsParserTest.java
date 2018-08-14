package cz.valkovic.java.twbot.services.parsers;

import cz.valkovic.java.twbot.services.logging.TestLoggingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TWStatsSettingsParserTest {

    private static Parser p;
    private static Document d;

    @BeforeAll
    static void beforeEach() throws IOException {
        File toRead = new File(TWStatsSettingsParserTest.class
                .getClassLoader()
                .getResource("twstats_settings.html")
                .getFile()
        );

        d = Jsoup.parse(toRead, "UTF8");

        p = new TWStatsSettingParser(new TestLoggingService());
    }

    @Test
    void shouldParseSettingsPageWithoutError() throws URISyntaxException {

        p.proccess(
                new URI("http://www.twstats.com/cs57/index.php?page=settings"),
                d
        );
    }


}
