package cz.valkovic.twbot.services.parsers;

import com.google.inject.AbstractModule;
import cz.valkovic.twbot.services.parsers.reporting.*;

public class ParsersModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ServerSettingsReportingService.class).to(ReportingService.class);
        bind(UnitsSettingsReportingService.class).to(ReportingService.class);
        bind(BuildingSettingsReportingService.class).to(ReportingService.class);
        bind(ReportingService.class).to(DatabaseReportingService.class);
    }
}
