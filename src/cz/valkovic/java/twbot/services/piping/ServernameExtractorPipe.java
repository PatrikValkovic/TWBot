package cz.valkovic.java.twbot.services.piping;

import cz.valkovic.java.twbot.models.Server;
import cz.valkovic.java.twbot.services.configuration.Configuration;
import cz.valkovic.java.twbot.services.database.DatabaseConnection;
import lombok.Getter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URL;

@Singleton
public class ServernameExtractorPipe implements ParsingPipe, ServerInformationProvider {

    @Getter
    private String name;
    @Getter
    private String host;
    @Getter
    private Server server;

    private Configuration conf;
    private DatabaseConnection connection;

    @Inject
    public ServernameExtractorPipe(Configuration conf, DatabaseConnection connection) {
        this.conf = conf;
        this.connection = connection;
    }

    @Override
    public boolean process(URL location, String content) {
        try {
            if (location.getHost().matches(conf.appDomainRegex())) {

                name = location.getHost().substring(0, location.getHost().indexOf("."));
                host = location.getHost().substring(location.getHost().indexOf(".") + 1);

                return true;
            } else if (location.getHost().equals(conf.twstatsDomain())) {

                name = location.getPath().substring(1, location.getPath().indexOf("/", 1));
                host = null;

                return true;
            } else
                return false;
        }
        finally {
            if (name != null)
                server = connection.entityManagerNoTransaction(db -> {
                    return db.find(Server.class, name);
                });
        }
    }
}
