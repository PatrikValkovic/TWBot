package cz.valkovic.twbot.modules.core.database.setting;

import cz.valkovic.twbot.modules.core.directories.DirectoriesService;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import java.nio.file.Paths;
import javax.inject.Inject;

public class DatabaseStaticSetting {

    private DatabasePrivateSetting dbSetting;
    private DirectoriesService dir;

    @Inject
    public DatabaseStaticSetting(SettingsProviderService settings, DirectoriesService dir) {
        this.dir = dir;

        settings.observe(DatabasePrivateSetting.class, s -> this.dbSetting = s);
    }

    /**
     * Path to the database file.
     */
    public String path() {
        return Paths.get(this.dir.getDataDir(), dbSetting.filepath()).toString();
    }

    /**
     * JDBC connection string.
     */
    public String connectionString() {
        return "jdbc:sqlite:" + this.path();
    }
}
