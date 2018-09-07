package cz.valkovic.java.twbot.services.parsers;

import com.google.inject.AbstractModule;
import cz.valkovic.java.twbot.services.parsers.reporting.DatabaseReportingService;
import cz.valkovic.java.twbot.services.parsers.reporting.ReportingService;
import cz.valkovic.java.twbot.services.parsers.reporting.ServerSettingsReportingService;
import cz.valkovic.java.twbot.services.parsers.reporting.UnitsSettingsReportingService;

public class ParsersModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ServerSettingsReportingService.class).to(ReportingService.class);
        bind(UnitsSettingsReportingService.class).to(ReportingService.class);
        bind(ReportingService.class).to(DatabaseReportingService.class);
    }
}
