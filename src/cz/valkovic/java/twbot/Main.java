package cz.valkovic.java.twbot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import cz.valkovic.java.twbot.runners.HibernateInitializationRunner;
import cz.valkovic.java.twbot.services.ServicesModule;
import cz.valkovic.java.twbot.services.configuration.ConfigurationService;
import cz.valkovic.java.twbot.services.database.DatabaseConnection;
import cz.valkovic.java.twbot.services.logging.LoggingModule;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import org.apache.logging.log4j.Logger;

public class Main {

    public static void main(String[] args) {
        Injector startupInjector = Guice.createInjector(new LoggingModule());
        LoggingService log = startupInjector.getInstance(LoggingService.class);
        Logger startup = log.getStartup();
        startup.info("Application startup");

        startup.info("Building dependency injection container");
        ServicesModule.getInjector();
        startup.info("Dependency injection container created");


        HibernateInitializationRunner.runInSeparateThread(ServicesModule.getInjector());


        Application.main(args);



        Logger exit = log.getExit();
        exit.info("CLosing database connections");
        ServicesModule.getInjector()
                      .getInstance(DatabaseConnection.class)
                      .close_noexc(exit);
        exit.info("Storing configuration");
        ServicesModule.getInjector()
                      .getInstance(ConfigurationService.class)
                      .save_noexc();
        exit.info("Configuration storing finished");
    }

}
