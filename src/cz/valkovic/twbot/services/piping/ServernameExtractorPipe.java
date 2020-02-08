package cz.valkovic.twbot.services.piping;

import cz.valkovic.twbot.models.Server;
import cz.valkovic.twbot.modules.core.database.DatabaseConnectionService;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.core.settings.instances.CorePrivateSetting;
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

    private DatabaseConnectionService connection;
    private CorePrivateSetting setting;

    @Inject
    public ServernameExtractorPipe(SettingsProviderService settingsProvider, DatabaseConnectionService connection) {
        this.connection = connection;

        settingsProvider.observe(CorePrivateSetting.class, s -> setting = s);
    }

    @Override
    public boolean process(URL location, String content) {
        try {
            if (location.getHost().matches(setting.appDomainRegex())) {

                name = location.getHost().substring(0, location.getHost().indexOf("."));
                host = location.getHost().substring(location.getHost().indexOf(".") + 1);

                return true;
            } else if (location.getHost().equals(setting.twstatsDomain())) {

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
