package cz.valkovic.java.twbot.services.piping;

import cz.valkovic.java.twbot.models.Server;

public interface ServerInformationProvider {

    String getName();

    String getHost();

    Server getServer();
}
