package cz.valkovic.java.twbot.services;

import com.google.inject.Inject;
import cz.valkovic.java.twbot.services.logging.LoggingService;

import java.io.IOException;
import java.net.URL;

public class ResourceLoaderService {

    @Inject
    private static LoggingService log;

    public static URL getResource(String name) throws IOException {
        log.getLoadingResources().debug("Loading " + name);
        URL url = ResourceLoaderService.class.getClassLoader().getResource(name);
        if (url == null) {
            log.getLoadingResources().warn("Resource " + name + " does not exists");
            throw new IOException("Resource " + name + " does not exists");
        }
        return url;
    }

}
