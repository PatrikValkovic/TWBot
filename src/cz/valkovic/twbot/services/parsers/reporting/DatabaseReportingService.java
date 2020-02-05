package cz.valkovic.twbot.services.parsers.reporting;

import cz.valkovic.twbot.models.*;
import cz.valkovic.twbot.modules.core.database.DatabaseConnectionService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.services.piping.ServerInformationProvider;
import javax.inject.Inject;

public class DatabaseReportingService implements ReportingService {

    private LoggingService log;
    private DatabaseConnectionService connection;
    private ServerInformationProvider serverInformation;

    @Inject
    public DatabaseReportingService(LoggingService log, DatabaseConnectionService connection, ServerInformationProvider serverInformation) {
        this.log = log;
        this.connection = connection;
        this.serverInformation = serverInformation;
    }

    @Override
    public void report(ServerSetting settings) {
        log.getPiping().info("Storing settings for " + serverInformation.getServer().getServerName());

        connection.entityManager(db -> {
            Server server = serverInformation.getServer();
            server.setSetting(settings);
            db.merge(server);
        });

        log.getPiping().info("Settings for " + serverInformation.getServer().getServerName() + " stored");
    }

    @Override
    public void report(UnitsSettings units) {
        log.getPiping().info("Storing units settings for " + serverInformation.getServer().getServerName());

        connection.entityManager(db -> {
            Server server = serverInformation.getServer();
            units.setServerName(server.getServerName());

            for(UnitInfo unit :units.getUnits()){
                unit.setUnitsSettings(units);
            }

            db.persist(units);
        });

        log.getPiping().info("Units settings for " + serverInformation.getServer().getServerName() + " stored");
    }

    @Override
    public void report(BuildingSettings buildings) {
        log.getPiping().info("Storing building settings for " + serverInformation.getServer().getServerName());

        connection.entityManager(db -> {
            Server server = serverInformation.getServer();
            buildings.setServerName(server.getServerName());

            for(BuildingInfo building: buildings.getBuildings()){
                building.setBuildingSettings(buildings);
            }

            db.persist(buildings);
        });

        log.getPiping().info("Building settings for " + serverInformation.getServer().getServerName() + " stored");
    }
}
