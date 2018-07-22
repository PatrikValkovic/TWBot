package cz.valkovic.java.twbot.services.logging;

import com.google.inject.AbstractModule;

public class LoggingModule extends AbstractModule {

    @Override
    protected void configure() {

        this.bind(LoggingService.class).to(Log4jLoggingService.class);

    }
}
