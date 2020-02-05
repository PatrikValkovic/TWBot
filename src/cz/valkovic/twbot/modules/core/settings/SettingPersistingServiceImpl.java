package cz.valkovic.twbot.modules.core.settings;

import cz.valkovic.twbot.modules.core.directories.DirectoriesService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import java.io.*;
import java.nio.file.Paths;
import javax.inject.Inject;
import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Mutable;

public class SettingPersistingServiceImpl implements SettingPersistingService {

    private final LoggingService log;
    private final DirectoriesService dir;

    @Inject
    public SettingPersistingServiceImpl(LoggingService log, DirectoriesService dir) {
        this.log = log;
        this.dir = dir;
    }

    private String getSettingFilePath(Class<?> cls) {
        return Paths.get(
                this.dir.getConfigDir(),
                cls.getCanonicalName().replace(".", "_") + ".conf"
        ).toString();
    }

    @Override
    public <T extends Mutable> T load(Class<T> type) {
        log.getSettings().info("Loading setting for " + type.getSimpleName());
        String filepath = this.getSettingFilePath(type);
        T config = ConfigFactory.create(type);
        if (new File(filepath).exists()) {
            log.getSettings().debug(String.format(
                    "File %s with setting for %s found.",
                    filepath,
                    type.getCanonicalName()
            ));
            try (InputStream istr = new FileInputStream(filepath)) {
                config.load(istr);
            }
            catch (IOException e) {
                log.getSettings().warn("Unable to load setting from " + filepath, e);
            }
        }
        log.getSettings().debug("Setting " + type.getSimpleName() + " created");
        return config;
    }

    @Override
    public <T extends Accessible> void store(Class<? extends T> type, T settings) {
        log.getSettings().info("Storing " + settings.getClass().getCanonicalName());
        String filepath = this.getSettingFilePath(type);
        try (OutputStream str = new FileOutputStream(filepath)) {
            settings.store(str, null);
        }
        catch (IOException e) {
            log.getSettings().error("Cannot save setting for " + settings.getClass().getCanonicalName());
            log.getSettings().debug(e, e);
        }
        log.getSettings().debug("Setting " + settings.getClass().getCanonicalName() + " stored");
    }
}
