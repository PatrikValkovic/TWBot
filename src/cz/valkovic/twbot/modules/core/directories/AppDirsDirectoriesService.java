package cz.valkovic.twbot.modules.core.directories;


import cz.valkovic.twbot.modules.core.logging.LoggingService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.harawata.appdirs.AppDirs;

@Singleton
public class AppDirsDirectoriesService implements DirectoriesService {

    private static final String APPNAME = "twbot";
    private static final String VERSION = "1.0";
    private static final String AUTHOR = "kowalsky";

    private LoggingService log;
    private AppDirs dirs;

    @Inject
    public AppDirsDirectoriesService(LoggingService log, AppDirs dirs) {
        this.log = log;
        this.dirs = dirs;
    }


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
