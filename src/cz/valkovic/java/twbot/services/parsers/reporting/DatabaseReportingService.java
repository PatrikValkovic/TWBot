package cz.valkovic.java.twbot.services.parsers.reporting;

import cz.valkovic.java.twbot.models.ServerSetting;
import cz.valkovic.java.twbot.models.UnitsSettings;

public class DatabaseReportingService implements ReportingService {
    @Override
    public void reportServerSettings(ServerSetting settings) {
        System.out.println("Settings");
    }

    @Override
    public void reportUnitsSettings(UnitsSettings units) {
        System.out.println("Units");
    }
}
