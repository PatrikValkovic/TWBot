package cz.valkovic.java.twbot.services.configuration;

import java.io.IOException;

public interface ConfigurationService {

    PublicConfiguration getPublicConfiguration();

    InterConfiguration getInterConfiguration();

    void save() throws IOException;
}
