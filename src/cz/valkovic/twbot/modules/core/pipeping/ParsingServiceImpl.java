package cz.valkovic.twbot.modules.core.pipeping;

import cz.valkovic.twbot.modules.core.directories.DirectoriesService;
import cz.valkovic.twbot.modules.core.execution.ExecutionService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.inject.Inject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ParsingServiceImpl implements ParsingService {

    private final PipesRetrieveService pipes;
    private final LoggingService log;
    private final ExecutionService exe;
    private final DirectoriesService dir;
    private PipesPrivateSetting sett;

    @Inject
    public ParsingServiceImpl(
            PipesRetrieveService pipes,
            LoggingService log,
            ExecutionService exe,
            SettingsProviderService setting,
            DirectoriesService dir) {
        this.pipes = pipes;
        this.log = log;
        this.exe = exe;
        this.dir = dir;

        setting.observe(PipesPrivateSetting.class, s -> this.sett = s);
    }


    @Override
    public void parse(URL url, String content) {
        log.getParsing().debug("Initializing parsing on " + url.toString());
        exe.run(() -> {
            Document doc = createDocument(content);

            if(doc == null){
                log.getPipeping().warn(String.format(
                        "Couldn't obtain document from %s",
                        url.toString()
                ));
                if(this.sett != null && this.sett.storeUnparsableWebsites()) {
                    Path path = Paths.get(
                            dir.getLogDir(),
                            System.currentTimeMillis() + ".html"
                    );
                    log.getPipeping().info("Website is going to be saved into " + path.toString());
                    try {
                        Files.createDirectories(path);
                        Files.writeString(path, content, StandardCharsets.UTF_8);
                    }
                    catch(IOException e){
                        this.log.getPipeping().warn(String.format(
                                "Couldn't save content of %s into file %s because of %s",
                                url.toString(), path.toString(), e.getMessage()
                        ));
                        this.log.getPipeping().debug(e, e);
                    }
                }
                return;
            }

            exe.run(() -> {
                List<ParsingPipe> p = pipes.getRelevantPipes(url, doc);
                log.getParsing().info(String.format(
                        "Found %d parsing pipes for url %s",
                        p.size(), url.toString()
                ));
                p.forEach(pipe -> exe.run(() -> pipe.process(url, doc)));
            });
        });
    }

    private Document createDocument(String content) {
        return Jsoup.parse(content);
    }
}
