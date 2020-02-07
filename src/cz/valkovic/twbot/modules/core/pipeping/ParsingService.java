package cz.valkovic.twbot.modules.core.pipeping;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Runs all the pipes and parse the content.
 */
public interface ParsingService {

    /**
     * Parse the website.
     * @param url URL of the website.
     * @param content Website content.
     */
    void parse(URL url, String content);

    /**
     * Parse the website.
     * @param url URL of the website.
     * @param content Website content.
     * @throws MalformedURLException When can't parse URL.
     */
    default void parse(String url, String content) throws MalformedURLException {
        this.parse(new URL(url), content);
    }

}
