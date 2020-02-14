package cz.valkovic.twbot.modules.core.pipeping;

import java.net.URL;
import org.jsoup.nodes.Document;

/**
 * Define parsing pipe - pipe that should scratch content from the webpage.
 */
public interface ParsingPipe extends Pipe {

    /**
     * Process the webpage and scratch data from it.
     * @param url Website url location.
     * @param content Content of the website.
     */
    void process(URL url, Document content);

}
