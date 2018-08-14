package cz.valkovic.java.twbot.services.parsers;

import cz.valkovic.java.twbot.models.ServerSetting;

public interface ServerSettingsReportingService {

    void reportServerSettings(ServerSetting settings);

}
