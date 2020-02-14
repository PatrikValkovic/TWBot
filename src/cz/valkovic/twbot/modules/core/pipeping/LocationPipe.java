package cz.valkovic.twbot.modules.core.pipeping;

import java.net.URL;
import org.jsoup.nodes.Document;

/**
 * Define location pipe - it's purpose is to decice, whether the URL or web content match some criteria.
 */
public interface LocationPipe extends Pipe {

    /**
     * Check the content and decide, whether the content match criteria.
     * @param url URL of the website.
     * @param content Website content.
     * @return True if the criteria match, false otherwise.
     */
    boolean match(URL url, Document content);

}
