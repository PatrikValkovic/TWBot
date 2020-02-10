package cz.valkovic.twbot.modules.parsing;

import com.google.inject.AbstractModule;
import cz.valkovic.twbot.modules.core.importing.TWModule;
import cz.valkovic.twbot.modules.parsing.handle.EventHandler;
import cz.valkovic.twbot.modules.parsing.handle.ParsingRequestService;
import cz.valkovic.twbot.modules.parsing.handle.ParsingRequestServiceImpl;
import cz.valkovic.twbot.modules.parsing.locations.ApplicationLocPipe;
import cz.valkovic.twbot.modules.parsing.locations.TWStatsLocPipe;
import cz.valkovic.twbot.modules.parsing.setting.ParsingSettingDemand;

@TWModule
public class ParsingModule extends AbstractModule {

    @Override
    protected void configure() {
        // setting
        requestStaticInjection(ParsingSettingDemand.class);

        // location pipes
        requestStaticInjection(ApplicationLocPipe.class);
        requestStaticInjection(TWStatsLocPipe.class);

        // register entities
        requestStaticInjection(EntitiesRegistration.class);

        // parsing
        bind(ParsingRequestService.class).to(ParsingRequestServiceImpl.class);
        requestStaticInjection(EventHandler.class);

        // automatic repeated parsing
        requestStaticInjection(RepeatedParsingRegistration.class);
    }

}
