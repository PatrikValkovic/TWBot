package cz.valkovic.java.twbot.services.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TWStatsSettingsParserUrlTest {

    private Parser p;

    @BeforeEach
    void beforeAll(){
        p = new TWStatsSettingParser();
    }

    @Test
    void shouldNotRespondToUnitsURI() throws URISyntaxException {
        URI u = new URI("http://www.twstats.com/en97/index.php?page=units");
        assertFalse(p.willProccess(u));
    }

    @Test
    void shouldNotRespondToBuildingURI() throws URISyntaxException {
        URI u = new URI("http://www.twstats.com/cs57/index.php?page=buildings");
        assertFalse(p.willProccess(u));
    }

    @Test
    void shouldRespondToConfigURI() throws URISyntaxException {
        URI u = new URI("http://www.twstats.com/cs57/index.php?page=settings");
        assertTrue(p.willProccess(u));
    }

    @Test
    void shouldNotRespondToGoogleURI() throws URISyntaxException {
        URI u = new URI("https://www.google.cz/search?q=twstats&ie=&oe=");
        assertFalse(p.willProccess(u));
    }

    @Test
    void shouldNotRespondToDivokekmenyURI() throws URISyntaxException {
        URI u = new URI("https://cs60.divokekmeny.cz/game.php?screen=overview&intro");
        assertFalse(p.willProccess(u));
    }

}
