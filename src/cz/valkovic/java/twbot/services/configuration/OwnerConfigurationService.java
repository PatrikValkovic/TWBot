package cz.valkovic.java.twbot.services.configuration;

import com.google.inject.Inject;
import cz.valkovic.java.twbot.services.directories.DirectoriesService;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Mutable;

import java.io.*;
import java.nio.file.Paths;

class OwnerConfigurationService implements ConfigurationService {

    @Inject
    private static DirectoriesService dirs;

    @Inject
    private static LoggingService log;


    private static synchronized <T extends Mutable> T loadConfiguration(Class<? extends T> type, String filepath){
        log.getLoading().info("Loading configuration for " + type.getSimpleName());
        T config = ConfigFactory.create(type);
        if (new File(filepath).exists()) {
            try (InputStream istr = new FileInputStream(filepath)) {
                config.load(istr);
            }
            catch (IOException e) {
                log.getLoading().warn("Unable to load config from " + filepath, e);
            }
        }
        log.getLoading().info("Configuration for " + type.getSimpleName() + " loaded");
        return config;
    }

    //region Configuration
    private static String getConfigFilepath() {
        return Paths.get(dirs.getConfigDir(), "config.txt").toString();
    }

    private static Configuration conf = null;

    private static synchronized Configuration getConf() {
        if (conf == null) {
            conf = loadConfiguration(Configuration.class, getConfigFilepath());
        }
        return conf;
    }

    @Override
    public Configuration getConfiguration() {
        return getConf();
    }
    //endregion
    //region InterConfiguration
    private static String getInterConfigFilepath() {
        return Paths.get(dirs.getConfigDir(), "interconfig.txt").toString();
    }

    private static InterConfiguration interConf = null;

    private static synchronized InterConfiguration getInterConf() {
        if (interConf == null) {
            interConf = loadConfiguration(InterConfiguration.class, getInterConfigFilepath());
        }
        return interConf;
    }

    @Override
    public InterConfiguration getInterConfiguration() {
        return getInterConf();
    }
    //endregion

    private <T extends Accessible> void saveConfiguration(T conf, String path) throws IOException{
        try (OutputStream str = new FileOutputStream(path)) {
            conf.store(str, null);
        }
        catch (IOException e) {
            log.getExit().warn("Cannot save configuration for " + conf.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    public void save() throws IOException {
        this.getInterConfiguration().setProperty("firstRun", "false");
        saveConfiguration(this.getConfiguration(), getConfigFilepath());
        saveConfiguration(this.getInterConfiguration(), getInterConfigFilepath());
    }

    @Override
    public void save_noexc() {
        try {
            this.save();
        }
        catch (IOException e){
            log.getExit().error("Configuration was not saved");
            log.getExit().debug(e, e);
        }
    }
}
