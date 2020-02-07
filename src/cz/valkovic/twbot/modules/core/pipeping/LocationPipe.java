package cz.valkovic.twbot.modules.core.pipeping;

import org.jsoup.nodes.Document;
import java.net.URL;

/**
 * Define location pipe - it's purpose is to decice, whether the URL or web content match some criteria.
 */
public interface LocationPipe {

    /**
     * Check the content and decide, whether the content match criteria.
     * @param url URL of the website.
     * @param content Website content.
     * @return True if the criteria match, false otherwise.
     */
    boolean match(URL url, Document content);

}
