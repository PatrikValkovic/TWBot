package cz.valkovic.twbot.services.parsers;

import cz.valkovic.twbot.services.logging.TestLoggingService;
import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TWStatsUnitParserUrlTest {

    private Parser p;

    @BeforeEach
    void beforeAll(){
        p = new TWStatsUnitParser(new TestLoggingService(), null);
    }

    @Test
    void shouldRespondToValidURL() throws Exception {
        URL u = new URL("http://www.twstats.com/en97/index.php?page=units");
        assertTrue(p.willProccess(u));
    }

    @Test
    void shouldNotRespondToBuildingURL() throws Exception {
        URL u = new URL("http://www.twstats.com/cs57/index.php?page=buildings");
        assertFalse(p.willProccess(u));
    }

    @Test
    void shouldNotRespondToConfigURL() throws Exception {
        URL u = new URL("http://www.twstats.com/cs57/index.php?page=settings");
        assertFalse(p.willProccess(u));
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
