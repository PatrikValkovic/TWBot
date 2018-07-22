package cz.valkovic.java.twbot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import cz.valkovic.java.twbot.services.ServicesModule;
import cz.valkovic.java.twbot.services.logging.LoggingModule;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import org.apache.logging.log4j.Logger;

public class Main {

    public static void main(String[] args) {
        Injector startupInjector = Guice.createInjector(new LoggingModule());
        Logger l = startupInjector.getInstance(LoggingService.class).getStartup();
        l.info("Application startup");

        l.info("Building dependency injection container");
        ServicesModule.getInjector();
        l.info("Dependency injection container created");

        Application.main(args);
    }

}
