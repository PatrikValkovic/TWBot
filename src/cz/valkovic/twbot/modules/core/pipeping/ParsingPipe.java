package cz.valkovic.twbot.modules.core.pipeping;

import org.jsoup.nodes.Document;
import java.net.URL;

/**
 * Define parsing pipe - pipe that should scratch content from the webpage.
 */
public interface ParsingPipe {

    /**
     * Process the webpage and scratch data from it.
     * @param url Website url location.
     * @param content Content of the website.
     */
    void process(URL url, Document content);

}
