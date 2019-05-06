package cz.valkovic.java.twbot;

import com.google.inject.Injector;
import cz.valkovic.java.twbot.runners.HibernateInitializationRunner;
import cz.valkovic.java.twbot.services.ServicesModule;
import cz.valkovic.java.twbot.services.configuration.ConfigurationService;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import cz.valkovic.java.twbot.services.messaging.MessageService;
import cz.valkovic.java.twbot.services.messaging.messages.ApplicationClosing;
import cz.valkovic.java.twbot.services.messaging.messages.ApplicationStart;
import org.apache.logging.log4j.Logger;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Injector i = ServicesModule.getInjector();
        LoggingService log = i.getInstance(LoggingService.class);
        log.getStartup().info("Dependency injection container created");
        i.getInstance(MessageService.class).invoke(new ApplicationStart());


        HibernateInitializationRunner.runInSeparateThread(ServicesModule.getInjector());


        Application.main(args);


        ServicesModule.getInjector()
                      .getInstance(MessageService.class)
                      .invoke(new ApplicationClosing(log.getExit()))
                      .waitToAllEvents();

        Logger exit = log.getExit();
        exit.info("Storing configuration");
        ServicesModule.getInjector()
                      .getInstance(ConfigurationService.class)
                      .save_noexc();
        exit.info("ConfigurationOwner storing finished");
    }

}
