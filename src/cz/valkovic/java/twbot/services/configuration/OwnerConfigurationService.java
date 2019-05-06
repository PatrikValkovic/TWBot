package cz.valkovic.java.twbot.services.configuration;

import cz.valkovic.java.twbot.services.directories.DirectoriesService;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import cz.valkovic.java.twbot.services.messaging.MessageService;
import cz.valkovic.java.twbot.services.messaging.messages.ApplicationClosing;
import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Mutable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;
import java.nio.file.Paths;

@Singleton
class OwnerConfigurationService implements ConfigurationService {


    @Inject
    public OwnerConfigurationService(DirectoriesService dirs,
                                     LoggingService log,
                                     MessageService mess) {
        this.dirs = dirs;
        this.log = log;
        this.mess = mess;

        conf = loadConfiguration(Configuration.class, getConfigFilepath());
        interConf = loadConfiguration(InterConfiguration.class, getInterConfigFilepath());
    }


    private DirectoriesService dirs;
    private LoggingService log;
    private MessageService mess;

    @Inject
    public void injectMessagingService(MessageService service)
    {
        this.mess = service;
        this.mess.listenTo(ApplicationClosing.class, e -> this.save());
    }

    private synchronized <T extends Mutable> T loadConfiguration(Class<? extends T> type, String filepath) {
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
    private Configuration conf;

    private String getConfigFilepath() {
        return Paths.get(dirs.getConfigDir(), "config.txt").toString();
    }

    @Override
    public Configuration getConfiguration() {
        return conf;
    }

    //endregion
    //region InterConfiguration
    private InterConfiguration interConf;

    private String getInterConfigFilepath() {
        return Paths.get(dirs.getConfigDir(), "interconfig.txt").toString();
    }

    @Override
    public InterConfiguration getInterConfiguration() {
        return interConf;
    }
    //endregion

    private <T extends Accessible> void saveConfiguration(T conf, String path) throws IOException {
        try (OutputStream str = new FileOutputStream(path)) {
            conf.store(str, null);
        }
        catch (IOException e) {
            log.getExit().warn("Cannot save configuration for " + conf.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    public void save() {
        try {
            this.log.getExit().info("Saving configuration");
            this.getInterConfiguration().setProperty("firstRun", "false");
            saveConfiguration(this.getConfiguration(), getConfigFilepath());
            saveConfiguration(this.getInterConfiguration(), getInterConfigFilepath());
        }
        catch (IOException e) {
            log.getExit().error("Configuration was not saved");
            log.getExit().debug(e, e);
        }
    }
}
