package cz.valkovic.java.twbot;

import com.google.inject.Injector;
import cz.valkovic.java.twbot.modules.ModulesLoader;
import cz.valkovic.java.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.java.twbot.modules.core.events.instances.ApplicationCloseEvent;
import cz.valkovic.java.twbot.modules.core.events.instances.ApplicationStartEvent;
import cz.valkovic.java.twbot.modules.core.logging.LoggingService;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Injector i = ModulesLoader.getInjector();
        LoggingService log = i.getInstance(LoggingService.class);
        log.getStartup().info("Dependency injection container created");
        i.getInstance(EventBrokerService.class).invoke(new ApplicationStartEvent());

        try {
            Application.main(args);
        }
        finally {
            i.getInstance(EventBrokerService.class)
             .invoke(new ApplicationCloseEvent(log.getExit()))
             .waitToAllEvents();
        }
    }

}
