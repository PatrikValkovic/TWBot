package cz.valkovic.twbot.services.piping;

import cz.valkovic.twbot.models.Server;

public interface ServerInformationProvider {

    String getName();

    String getHost();

    Server getServer();
}
