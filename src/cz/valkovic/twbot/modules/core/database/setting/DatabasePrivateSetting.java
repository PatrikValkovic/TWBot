package cz.valkovic.twbot.modules.core.database.setting;

import cz.valkovic.twbot.modules.core.settings.StorableSettings;

public interface DatabasePrivateSetting extends StorableSettings {

    /**
     * Name of the database file.
     */
    @DefaultValue("database.db")
    String filepath();

    /**
     * Type of hibernate creation process.
     */
    @DefaultValue("validate")
    String hbm2ddl();
}
