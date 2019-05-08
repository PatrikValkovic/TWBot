package cz.valkovic.java.twbot.services.browserManipulation;

import com.google.inject.AbstractModule;

public class BrowserManipulationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ActionsService.class).to(ActionServiceImpl.class);

        requestStaticInjection(ActionServiceImpl.class);
    }
}
