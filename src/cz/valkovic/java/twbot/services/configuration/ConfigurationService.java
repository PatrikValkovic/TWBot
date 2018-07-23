package cz.valkovic.java.twbot.services.configuration;

import java.io.IOException;

public interface ConfigurationService {

    Configuration getConfiguration();

    void save() throws IOException;

}
