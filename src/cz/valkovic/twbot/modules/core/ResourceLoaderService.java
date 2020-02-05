package cz.valkovic.twbot.modules.core;


import cz.valkovic.twbot.modules.core.logging.LoggingService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ResourceLoaderService {

    @Inject
    private LoggingService log;

    public URL getResource(String name) throws IOException {
        log.getLoadingResources().debug("Loading " + name);
        URL url = ResourceLoaderService.class.getClassLoader().getResource(name);
        if (url == null) {
            log.getLoadingResources().warn("Resource " + name + " does not exists");
            throw new IOException("Resource " + name + " does not exists");
        }
        return url;
    }

    public String getResoureContent(String name) throws IOException, URISyntaxException {
        URL location = this.getResource(name);
        Path loc = Paths.get(location.toURI());
        List<String> lines = Files.readAllLines(loc, StandardCharsets.UTF_8);
        return String.join("", lines);
    }

}