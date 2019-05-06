package cz.valkovic.java.twbot.services.configuration;

import java.io.IOException;

public interface ConfigurationService {

    Configuration getConfiguration();

    InterConfiguration getInterConfiguration();

    void save() throws IOException;
}
