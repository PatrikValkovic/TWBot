package cz.valkovic.java.twbot.services.configuration;

import com.google.inject.Inject;
import cz.valkovic.java.twbot.services.directories.DirectoriesService;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import org.aeonbits.owner.ConfigFactory;

import java.io.*;
import java.nio.file.Paths;

class OwnerConfigurationService implements ConfigurationService {

    private static Configuration conf = null;

    private static synchronized Configuration getConf(String filepath, LoggingService log) {
        if (conf == null) {
            log.getLoading().info("Loading configuration");
            conf = ConfigFactory.create(Configuration.class);
            if (new File(filepath).exists()) {
                try (InputStream istr = new FileInputStream(filepath)) {
                    conf.load(istr);
                }
                catch (IOException e) {
                    log.getLoading().warn("Unable to load config from " + filepath, e);
                }
            }
            log.getLoading().info("Configuration loaded");
        }
        return conf;
    }


    @Inject
    private DirectoriesService dirs;

    @Inject
    private LoggingService log;

    private String getConfigFilepath() {
        return Paths.get(this.dirs.getConfigDir(), "config.txt").toString();
    }

    @Override
    public Configuration getConfiguration() {
        String path = this.getConfigFilepath();
        return getConf(path, this.log);
    }

    @Override
    public void save() throws IOException {
        String path = this.getConfigFilepath();
        try {
            Configuration c = getConf(path, this.log);
            try (OutputStream str = new FileOutputStream(path)) {
                c.store(str, null);
            }
        }
        catch (IOException e) {
            this.log.getStartup().warn("Cannot save configuration", e);
            throw e;
        }
    }
}
