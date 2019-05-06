package cz.valkovic.java.twbot.services;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import cz.valkovic.java.twbot.services.configuration.ConfigurationModule;
import cz.valkovic.java.twbot.services.connectors.ConnectorsModule;
import cz.valkovic.java.twbot.services.database.DatabaseModule;
import cz.valkovic.java.twbot.services.directories.DirectoriesModule;
import cz.valkovic.java.twbot.services.logging.LoggingModule;
import cz.valkovic.java.twbot.services.messaging.MessagingModule;
import cz.valkovic.java.twbot.services.parsers.ParsersModule;
import cz.valkovic.java.twbot.services.piping.PipingModule;

public class ServicesModule extends AbstractModule {

    private static Injector injector;

    public static Injector getInjector(){
        if(injector == null){
            injector = Guice.createInjector(new ServicesModule());
        }
        return injector;
    }

    @Override
    protected void configure() {
        install(new LoggingModule());
        install(new DirectoriesModule());
        install(new ConfigurationModule());
        install(new DatabaseModule());
        install(new ConnectorsModule());
        install(new PipingModule());
        install(new ParsersModule());
        install(new MessagingModule());
        bind(ResourceLoaderService.class);
    }
}
