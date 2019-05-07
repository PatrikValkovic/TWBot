package cz.valkovic.java.twbot.services;


import cz.valkovic.java.twbot.services.logging.LoggingService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
        List<String> lines = Files.readAllLines(loc, Charset.forName("utf8"));
        return String.join("", lines);
    }

}
