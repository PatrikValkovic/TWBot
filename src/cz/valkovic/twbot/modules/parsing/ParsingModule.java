package cz.valkovic.twbot.modules.parsing;

import com.google.inject.AbstractModule;
import cz.valkovic.twbot.modules.core.importing.TWModule;

@TWModule
public class ParsingModule extends AbstractModule {

    @Override
    protected void configure() {
        requestStaticInjection(EntitiesRegistration.class);
    }

}
