package cz.valkovic.twbot.modules.core.tabs;

import cz.valkovic.twbot.modules.core.directories.DirectoriesService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;

public class LastSessionTabsImpl implements LastSessionTabs {

    private static final String FILENAME = "tabs.txt";

    private DirectoriesService dirs;
    private LoggingService log;

    @Inject
    public LastSessionTabsImpl(DirectoriesService dirs, LoggingService log) {
        this.dirs = dirs;
        this.log = log;
    }

    @Override
    public String[] openedTabs() {
        Path p = Paths.get(this.dirs.getDataDir(), FILENAME);
        File f = new File(p.toString());
        this.log.getStartup().info("Attempt to load tabs from previous session");
        if (!f.exists()) {
            this.log.getStartup().debug("File for tabs from previous session doesn't exists");
            return new String[0];
        }
        try {
            String content = Files.readString(p, StandardCharsets.UTF_8);
            return Stream.of(content.split(String.valueOf((char)1)))
                         .toArray(String[]::new);
        }
        catch (IOException e) {
            this.log.getStartup().warn("Error reading tabs file");
            this.log.getStartup().debug(e, e);
            return new String[0];
        }
    }

    @Override
    public void storeOpenedTabs(String[] tabs) {
        Path p = Paths.get(this.dirs.getDataDir(), FILENAME);
        File f = new File(p.toString());
        try {
            this.log.getExit().debug("Storing tabs from the session");
            String finalString = Stream.of(tabs)
                                       .collect(Collectors.joining(String.valueOf((char)1)));
            Files.writeString(p, finalString, StandardCharsets.UTF_8);
            this.log.getExit().info("Storing tabs from the session");
        }
        catch (IOException e) {
            this.log.getExit().error("Error writing tabs file");
            this.log.getStartup().debug(e, e);
        }
    }
}
