package cz.valkovic.java.twbot.services.parsers;

import cz.valkovic.java.twbot.services.logging.TestLoggingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TWStatsSettingsParserUrlTest {

    private Parser p;

    @BeforeEach
    void beforeAll(){
        p = new TWStatsSettingParser(new TestLoggingService(), null);
    }

    @Test
    void shouldNotRespondToUnitsURL() throws Exception {
        URL u = new URL("http://www.twstats.com/en97/index.php?page=units");
        assertFalse(p.willProccess(u));
    }

    @Test
    void shouldNotRespondToBuildingURL() throws Exception {
        URL u = new URL("http://www.twstats.com/cs57/index.php?page=buildings");
        assertFalse(p.willProccess(u));
    }

    @Test
    void shouldRespondToConfigURL() throws Exception {
        URL u = new URL("http://www.twstats.com/cs57/index.php?page=settings");
        assertTrue(p.willProccess(u));
    }

    @Test
    void shouldNotRespondToGoogleURL() throws Exception {
        URL u = new URL("https://www.google.cz/search?q=twstats&ie=&oe=");
        assertFalse(p.willProccess(u));
    }

    @Test
    void shouldNotRespondToDivokekmenyURL() throws Exception {
        URL u = new URL("https://cs60.divokekmeny.cz/game.php?screen=overview&intro");
        assertFalse(p.willProccess(u));
    }

}
