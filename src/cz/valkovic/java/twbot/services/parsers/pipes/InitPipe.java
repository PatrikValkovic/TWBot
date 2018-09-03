package cz.valkovic.java.twbot.services.parsers.pipes;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.net.URL;

@Singleton
public class InitPipe implements ParsingPipe {

    private ParsingPipe pipe = null;

    @Inject
    public void init(Provider<SeriesPipe> series,
                     Provider<ShouldParsePipe> shouldParse,
                     Provider<TWStatConfigurationPipe> twStatConfiguration) {
        pipe = series.get()
                     .add(shouldParse.get())
                     .add(twStatConfiguration.get());
    }

    @Override
    public boolean process(URL location, String content) {
        return pipe.process(location, content);
    }
}
