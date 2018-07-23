package cz.valkovic.java.twbot.services.directories;

import com.google.inject.Inject;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import net.harawata.appdirs.AppDirs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AppDirsDirectoriesService implements DirectoriesService {

    //TODO remove hardcoded version

    @Inject
    private LoggingService log;

    @Inject
    private AppDirs dirs;

    @Override
    public String getConfigDir() {
        String path = dirs.getUserConfigDir("twbot","0.1","kowalsky",true);
        try {
            Files.createDirectories(Paths.get(path));
        }
        catch(IOException e){
            this.log.getLoading().warn("Cannot create config dir " + path);
            this.log.getLoading().debug(e, e);
        }
        return path;
    }

    @Override
    public String getDataDir() {
        String path = dirs.getUserDataDir("twbot","0.1","kowalsky");
        try {
            Files.createDirectories(Paths.get(path));
            return path;
        }
        catch(IOException e){
            this.log.getLoading().warn("Cannot create data dir " + path);
            this.log.getLoading().debug(e, e);
        }
        return path;
    }

    @Override
    public String getLogDir() {
        String path = dirs.getUserLogDir("twbot","0.1","kowalsky");
        try {
            Files.createDirectories(Paths.get(path));
            return path;
        }
        catch(IOException e){
            this.log.getLoading().warn("Cannot create log dir " + path);
            this.log.getLoading().debug(e, e);
        }
        return path;
    }
}
