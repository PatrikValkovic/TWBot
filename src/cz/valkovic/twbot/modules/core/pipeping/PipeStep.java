package cz.valkovic.twbot.modules.core.pipeping;

import cz.valkovic.twbot.modules.core.logging.LoggingService;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.inject.Inject;
import org.jsoup.nodes.Document;

class PipeStep {

    private List<Map.Entry<LocationPipe, String>> locationPipes = new LinkedList<>();
    private List<ParsingPipe> parsingPipes = new LinkedList<>();

    private final LoggingService log;

    @Inject
    public PipeStep(LoggingService log) {
        this.log = log;
    }

    void addParsingPipe(ParsingPipe pipe) {
        this.parsingPipes.add(pipe);
    }

    void addLocationPipe(LocationPipe pipe, String namespace) {
        this.locationPipes.add(Map.entry(pipe, namespace));
    }

    Stream<ParsingPipe> getParsingPipes() {
        return parsingPipes.stream();
    }

    Stream<String> getRelevantLocations(URL url, Document content) {
        return this.locationPipes
                .stream()
                .filter(entry -> {
                    try {
                        return entry.getKey().match(url, content);
                    }
                    catch(Exception e){
                        this.log.getPipeping().warn(String.format(
                                "Exception in matching the url of pipe %s",
                                entry.getKey().getClass().getCanonicalName()
                        ));
                        return false;
                    }
                })
                .map(Map.Entry::getValue);
    }
}
