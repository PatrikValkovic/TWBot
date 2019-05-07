package cz.valkovic.java.twbot;

import com.google.inject.Injector;
import cz.valkovic.java.twbot.runners.HibernateInitializationRunner;
import cz.valkovic.java.twbot.services.ServicesModule;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import cz.valkovic.java.twbot.services.messaging.MessageService;
import cz.valkovic.java.twbot.services.messaging.messages.ApplicationClosing;
import cz.valkovic.java.twbot.services.messaging.messages.ApplicationStart;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Injector i = ServicesModule.getInjector();
        LoggingService log = i.getInstance(LoggingService.class);
        log.getStartup().info("Dependency injection container created");
        i.getInstance(MessageService.class).invoke(new ApplicationStart());

        try {
            HibernateInitializationRunner.runInSeparateThread(ServicesModule.getInjector());

            Application.main(args);
        }
        finally {
            ServicesModule.getInjector()
                          .getInstance(MessageService.class)
                          .invoke(new ApplicationClosing(log.getExit()))
                          .waitToAllEvents();
        }
    }

}
