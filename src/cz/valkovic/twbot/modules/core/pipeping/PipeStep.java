package cz.valkovic.twbot.modules.core.pipeping;

import org.jsoup.nodes.Document;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class PipeStep {

    private List<Map.Entry<LocationPipe, String>> locationPipes = new LinkedList<>();
    private List<ParsingPipe> parsingPipes = new LinkedList<>();

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
                .filter(entry -> entry.getKey().match(url, content))
                .map(Map.Entry::getValue);
    }
}
