package cz.valkovic.twbot.services;

import com.google.inject.AbstractModule;
import cz.valkovic.twbot.modules.core.importing.TWModule;
import cz.valkovic.twbot.services.connectors.ConnectorsModule;
import cz.valkovic.twbot.services.parsers.ParsersModule;
import cz.valkovic.twbot.services.piping.PipingModule;

@TWModule
public class ServicesModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new ConnectorsModule());
        install(new PipingModule());
        install(new ParsersModule());
    }
}
