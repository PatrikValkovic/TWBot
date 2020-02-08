package cz.valkovic.twbot.modules.core.pipeping;

import com.google.inject.Injector;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import org.jsoup.nodes.Document;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class PipesContainer implements PipesRegistrationService, PipesRetrieveService {

    private final Injector injector;
    private final LoggingService log;
    private Map<String, PipeStep> pipes = new HashMap<>(16, 0.5f);

    @Inject
    public PipesContainer(Injector injector, LoggingService log) {
        this.injector = injector;
        this.log = log;

        // create the first pipe
        this.pipes.put(PIPES.EVERYTHING.toString(), this.injector.getInstance(PipeStep.class));
    }

    @Override
    public void registerLocationPipe(String from, String namespace, Class<? extends LocationPipe> pipeClass) {
        this.log.getPipeping().debug(String.format(
                "Registering pipe on %s defining %s using class %s",
                from, namespace, pipeClass.getCanonicalName()
        ));

        if (!this.pipes.containsKey(from)) {
            this.log.getPipeping().warn(String.format(
                    "Namespace %s not defined for pipe %s",
                    from, pipeClass.getCanonicalName()
            ));
            this.pipes.put(from, this.injector.getInstance(PipeStep.class));
        }
        if (!this.pipes.containsKey(namespace)) {
            this.log.getPipeping().debug(String.format(
                    "Namespace %s creating",
                    namespace
            ));
            this.pipes.put(namespace, this.injector.getInstance(PipeStep.class));
        }

        LocationPipe instance = injector.getInstance(pipeClass);
        this.pipes.get(from).addLocationPipe(instance, namespace);
        this.log.getPipeping().info(String.format(
                "Registered pipe from %s to %s using class %s",
                from, namespace, pipeClass.getCanonicalName()
        ));
    }

    @Override
    public void registerParsingPipe(String from, Class<? extends ParsingPipe> pipeClass) {
        this.log.getPipeping().debug(String.format(
                "Registering pipe on %s using class %s",
                from, pipeClass.getCanonicalName()
        ));

        if (!this.pipes.containsKey(from)) {
            this.log.getPipeping().warn(String.format(
                    "Namespace %s not defined for pipe %s",
                    from, pipeClass.getCanonicalName()
            ));
            this.pipes.put(from, this.injector.getInstance(PipeStep.class));
        }

        ParsingPipe instance = injector.getInstance(pipeClass);
        this.pipes.get(from).addParsingPipe(instance);
        this.log.getPipeping().info(String.format(
                "Registered pipe from %s using class %s",
                from, pipeClass.getCanonicalName()
        ));
    }

    @Override
    public List<ParsingPipe> getRelevantPipes(URL url, Document content) {
        return this.getRelevantLocations(url, content)
                   .stream()
                   .flatMap(location -> {
                       PipeStep step = pipes.get(location);
                       return step.getParsingPipes();
                   })
                   .collect(Collectors.toList());
    }

    private List<String> getRelevantLocations(URL url, Document content) {
        Set<String> processed = new HashSet<>(this.pipes.size() * 2);
        Queue<String> toProcess = new LinkedList<>();
        toProcess.add(PIPES.EVERYTHING.toString());
        List<String> relevant = new LinkedList<>();

        while(toProcess.size() > 0) {
            String processing = toProcess.poll();
            if(processed.contains(processing))
                continue;
            relevant.add(processing);
            processed.add(processing);
            toProcess.addAll(
                    pipes.get(processing).getRelevantLocations(url, content).collect(Collectors.toList())
            );
        }
        this.log.getPipeping().debug(String.format(
                "User is now in the following locations: %s",
                relevant.stream().collect(Collectors.joining(";"))
        ));
        return relevant;
    }
}
