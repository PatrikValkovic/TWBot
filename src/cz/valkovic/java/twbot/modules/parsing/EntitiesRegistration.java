package cz.valkovic.java.twbot.modules.parsing;

import cz.valkovic.java.twbot.models.*;
import cz.valkovic.java.twbot.modules.core.database.EntityRegistrationService;
import javax.inject.Inject;

public class EntitiesRegistration {

    @Inject
    static void register(EntityRegistrationService reg) {
        reg.registerEntity(Server.class);
        reg.registerEntity(ServerSetting.class);
        reg.registerEntity(UnitsSettings.class);
        reg.registerEntity(UnitInfo.class);
        reg.registerEntity(BuildingSettings.class);
        reg.registerEntity(BuildingInfo.class);
    }
}
