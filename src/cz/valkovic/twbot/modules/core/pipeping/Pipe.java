package cz.valkovic.twbot.modules.core.pipeping;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Common interface for all pipes.
 */
public interface Pipe {

    /**
     * Parse GET parameters from the URL>
     * @param location URL from which parse the parameters.
     * @return Map of GET parameters.
     */
    default Map<String, String> getParameters(URL location) {
        if(location.getQuery() == null)
            return new HashMap<>(0);

        return Stream.of(location.getQuery().split("&"))
                     .map(parameter -> {
                         if(parameter.contains("="))
                             return parameter.split("=");
                         return new String[] {parameter, ""};
                     })
                     .collect(Collectors.toMap(p -> p[0], p -> p[1]));
    }

}
