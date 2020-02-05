package cz.valkovic.java.twbot.services;

import com.google.inject.AbstractModule;
import cz.valkovic.java.twbot.services.browserManipulation.BrowserManipulationModule;
import cz.valkovic.java.twbot.services.configuration.ConfigurationModule;
import cz.valkovic.java.twbot.services.connectors.ConnectorsModule;
import cz.valkovic.java.twbot.services.parsers.ParsersModule;
import cz.valkovic.java.twbot.services.piping.PipingModule;

public class ServicesModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new ConfigurationModule());
        install(new ConnectorsModule());
        install(new PipingModule());
        install(new ParsersModule());
        install(new BrowserManipulationModule());
    }
}
