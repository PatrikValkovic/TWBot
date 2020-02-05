package cz.valkovic.twbot.services.piping;

import cz.valkovic.twbot.models.Server;
import cz.valkovic.twbot.modules.core.database.DatabaseConnectionService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.services.navigation.NavigationService;
import java.net.URL;
import javax.inject.Inject;

public class TWStatsConfigurationPipe implements ParsingPipe {

    private LoggingService log;
    private DatabaseConnectionService connection;
    private NavigationService navigation;
    private ServerInformationProvider serverInformation;

    @Inject
    public TWStatsConfigurationPipe(LoggingService log, DatabaseConnectionService connection, NavigationService navigation, ServerInformationProvider serverInformation) {
        this.log = log;
        this.connection = connection;
        this.navigation = navigation;
        this.serverInformation = serverInformation;
    }

    @Override
    public boolean process(URL location, String content) {
        String server = serverInformation.getName();

        log.getPiping().info("Check if server " + server + " exists");

        return connection.entityManager(db -> {
            Server serverDb = db.find(Server.class, server);

            //server exists
            if(serverDb != null) {
                if(serverInformation.getHost() != null && serverDb.getHost() == null){
                    serverDb.setHost(serverInformation.getHost());
                    db.merge(serverDb);
                }
                return true;
            }

            //create server instance
            Server s = new Server();
            s.setHost(serverInformation.getHost());
            s.setServerName(serverInformation.getName());
            db.persist(s);

            //navigate to info pages
            log.getPiping().info("Server " + server + " does not exists, navigating to twstats");
            navigation.queue("http://www.twstats.com/"+ server +"/index.php?page=settings");
            navigation.queue("http://www.twstats.com/"+ server +"/index.php?page=units");
            navigation.queue("http://www.twstats.com/"+ server +"/index.php?page=buildings");
            navigation.queue(location);

            return false;
        });

    }
}
