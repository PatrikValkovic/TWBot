package cz.valkovic.java.twbot.services.configuration;

import cz.valkovic.java.twbot.services.directories.DirectoriesService;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import cz.valkovic.java.twbot.services.messaging.MessageService;
import cz.valkovic.java.twbot.services.messaging.messages.ApplicationClosing;
import cz.valkovic.java.twbot.services.messaging.messages.SettingsChanged;
import cz.valkovic.java.twbot.services.messaging.messages.SettingsChangedAttempt;
import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Mutable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Singleton
class OwnerConfigurationService implements ConfigurationService {


    @Inject
    public OwnerConfigurationService(DirectoriesService dirs,
                                     LoggingService log,
                                     MessageService mess) {
        this.dirs = dirs;
        this.log = log;
        this.mess = mess;

        pubConf = loadConfiguration(PublicConfiguration.class, getPublicConfigFilepath());
        interConf = loadConfiguration(InterConfiguration.class, getInterConfigFilepath());

        mess.listenTo(SettingsChangedAttempt.class, (ev) -> {
            Set<String> currentProps = new HashSet<>(this.pubConf.propertyNames());
            currentProps.removeAll(ev.getProps().keySet());
            if(currentProps.size() > 0){
                log.getSettings().warn("Not all setting properties were set");
                log.getSettings().debug(this.pubConf.propertyNames());
                log.getSettings().debug(ev.getProps().keySet());
            }
            for(String k : this.pubConf.propertyNames()){
                this.pubConf.setProperty(k, ev.getProps().get(k));
            }
            log.getSettings().info("Setting set up");
            mess.invoke(new SettingsChanged(this.pubConf));
        });

        mess.listenTo(SettingsChanged.class, (ev) -> this.save());
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
        log.getLoading().info("PublicConfiguration for " + type.getSimpleName() + " loaded");
        return config;
    }

    //region PublicConfiguration
    private PublicConfiguration pubConf;

    private String getPublicConfigFilepath() {
        return Paths.get(dirs.getConfigDir(), "config.txt").toString();
    }

    @Override
    public PublicConfiguration getPublicConfiguration() {
        return pubConf;
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
            saveConfiguration(this.getPublicConfiguration(), getPublicConfigFilepath());
            saveConfiguration(this.getInterConfiguration(), getInterConfigFilepath());
        }
        catch (IOException e) {
            log.getExit().error("PublicConfiguration was not saved");
            log.getExit().debug(e, e);
        }
    }
}
