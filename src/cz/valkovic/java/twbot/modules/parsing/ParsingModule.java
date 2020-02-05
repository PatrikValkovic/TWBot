package cz.valkovic.java.twbot.modules.parsing;

import com.google.inject.AbstractModule;

public class ParsingModule extends AbstractModule {

    @Override
    protected void configure() {
        requestStaticInjection(EntitiesRegistration.class);
    }

}
