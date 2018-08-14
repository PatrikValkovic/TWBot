package cz.valkovic.java.twbot.services.directories;


import cz.valkovic.java.twbot.services.logging.LoggingService;
import net.harawata.appdirs.AppDirs;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Singleton
public class AppDirsDirectoriesService implements DirectoriesService {

    private static final String APPNAME = "twbot";
    private static final String VERSION = "1.0";
    private static final String AUTHOR = "kowalsky";

    @Inject
    public AppDirsDirectoriesService(LoggingService log, AppDirs dirs) {
        this.log = log;
        this.dirs = dirs;
    }

    private LoggingService log;

    private AppDirs dirs;

    @Override
    public String getConfigDir() {
        String path = dirs.getUserConfigDir(APPNAME, VERSION, AUTHOR, true);
        try {
            Files.createDirectories(Paths.get(path));
        }
        catch (IOException e) {
            this.log.getLoading().warn("Cannot create config dir " + path);
            this.log.getLoading().debug(e, e);
        }
        return path;
    }

    @Override
    public String getDataDir() {
        String path = dirs.getUserDataDir(APPNAME, VERSION, AUTHOR);
        try {
            Files.createDirectories(Paths.get(path));
            return path;
        }
        catch (IOException e) {
            this.log.getLoading().warn("Cannot create data dir " + path);
            this.log.getLoading().debug(e, e);
        }
        return path;
    }

    @Override
    public String getLogDir() {
        String path = dirs.getUserLogDir(APPNAME, VERSION, AUTHOR);
        try {
            Files.createDirectories(Paths.get(path));
            return path;
        }
        catch (IOException e) {
            this.log.getLoading().warn("Cannot create log dir " + path);
            this.log.getLoading().debug(e, e);
        }
        return path;
    }
}
