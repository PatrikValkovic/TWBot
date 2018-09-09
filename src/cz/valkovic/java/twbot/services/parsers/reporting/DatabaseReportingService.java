package cz.valkovic.java.twbot.services.parsers.reporting;

import cz.valkovic.java.twbot.models.Server;
import cz.valkovic.java.twbot.models.ServerSetting;
import cz.valkovic.java.twbot.models.UnitInfo;
import cz.valkovic.java.twbot.models.UnitsSettings;
import cz.valkovic.java.twbot.services.database.DatabaseConnection;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import cz.valkovic.java.twbot.services.piping.ServerInformationProvider;

import javax.inject.Inject;

public class DatabaseReportingService implements ReportingService {

    private LoggingService log;
    private DatabaseConnection connection;
    private ServerInformationProvider serverInformation;

    @Inject
    public DatabaseReportingService(LoggingService log, DatabaseConnection connection, ServerInformationProvider serverInformation) {
        this.log = log;
        this.connection = connection;
        this.serverInformation = serverInformation;
    }

    @Override
    public void reportServerSettings(ServerSetting settings) {
        log.getPiping().info("Storing settings for " + serverInformation.getServer().getServerName());

        connection.entityManager(db -> {
            Server server = serverInformation.getServer();
            server.setSetting(settings);
            db.merge(server);
        });

        System.out.println("Settings for " + serverInformation.getServer().getServerName() + " stored");
    }

    @Override
    public void reportUnitsSettings(UnitsSettings units) {
        log.getPiping().info("Storing units settings for " + serverInformation.getServer().getServerName());

        connection.entityManager(db -> {
            Server server = serverInformation.getServer();
            units.setServerName(server.getServerName());

            for(UnitInfo unit :units.getUnits()){
                unit.setUnitsSettings(units);
            }

            db.persist(units);
        });

        System.out.println("Units settings for " + serverInformation.getServer().getServerName() + " stored");
    }
}
