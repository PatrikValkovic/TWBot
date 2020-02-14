package cz.valkovic.twbot.modules.parsing.parsers;

import cz.valkovic.twbot.modules.core.pipeping.PipesRegistrationService;
import cz.valkovic.twbot.modules.parsing.LOCATIONS;
import javax.inject.Inject;

public class ParsingPipesRegistration {

    @Inject
    public static void register(PipesRegistrationService pipes){
        pipes.registerParsingPipe(
                LOCATIONS.LOGIN.toString(),
                LoginParser.class
        );
        pipes.registerParsingPipe(
                LOCATIONS.SERVER_PICKUP.toString(),
                ServerPickupParser.class
        );
    }
}
