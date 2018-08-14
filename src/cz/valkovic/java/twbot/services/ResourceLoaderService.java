package cz.valkovic.java.twbot.services;


import cz.valkovic.java.twbot.services.logging.LoggingService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.URL;

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

}
