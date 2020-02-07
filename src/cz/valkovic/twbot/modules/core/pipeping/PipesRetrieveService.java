package cz.valkovic.twbot.modules.core.pipeping;

import org.jsoup.nodes.Document;
import java.net.URL;
import java.util.List;

/**
 * Service to obtain pipes that should be executed on the current content.
 */
public interface PipesRetrieveService {

    /**
     * Get all the pipes that should be executed on current content.
     * @param url URL of the website.
     * @param content Website content.
     * @return List of pipes, that should be used on the current content.
     */
    List<ParsingPipe> getRelevantPipes(URL url, Document content);

}
