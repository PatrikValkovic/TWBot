package cz.valkovic.twbot;

import com.google.inject.Injector;
import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.twbot.modules.core.events.instances.ApplicationCloseEvent;
import cz.valkovic.twbot.modules.core.events.instances.ApplicationStartEvent;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import lombok.Getter;

public class Main {

    @Getter
    private static Injector injector = ModulesRegistration.createInjector();

    public static void main(String[] args) throws InterruptedException {
        Injector i = Main.getInjector();

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
