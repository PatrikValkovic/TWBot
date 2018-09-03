package cz.valkovic.java.twbot.services.parsers.pipes;

import cz.valkovic.java.twbot.models.Server;
import cz.valkovic.java.twbot.services.connectors.navigation.NavigationMiddleware;
import cz.valkovic.java.twbot.services.database.DatabaseConnection;
import cz.valkovic.java.twbot.services.logging.LoggingService;

import javax.inject.Inject;
import java.net.URL;

public class TWStatConfigurationPipe implements ParsingPipe {

    private LoggingService log;
    private DatabaseConnection connection;
    private NavigationMiddleware navigation;

    @Inject
    public TWStatConfigurationPipe(LoggingService log, DatabaseConnection connection, NavigationMiddleware navigation) {
        this.log = log;
        this.connection = connection;
        this.navigation = navigation;
    }

    @Override
    public boolean process(URL location, String content) {
        String server = location.getHost().substring(0, location.getHost().indexOf("."));

        log.getPiping().info("Check if server " + server + " exists ");

        return connection.entityManagerCallback(db -> {
            Server serverDb = db.find(Server.class, server);
            if(serverDb != null)
                return true;

            log.getPiping().info("Server " + server + " does not exists, navigating to twstats");
            navigation.queue("http://www.twstats.com/"+ server +"/index.php?page=settings");
            navigation.queue("http://www.twstats.com/"+ server +"/index.php?page=units");
            navigation.queue("http://www.twstats.com/"+ server +"/index.php?page=buildings");
            navigation.queue(location);

            return false;
        });

    }
}
