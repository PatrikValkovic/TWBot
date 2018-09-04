package cz.valkovic.java.twbot.services.parsers.pipes;

import lombok.Getter;

import javax.inject.Singleton;
import java.net.URL;

@Singleton
public class ServernameExtractorPipe implements ParsingPipe, ServerInformationProvider {

    @Getter
    private String name;
    @Getter
    private String host;

    @Override
    public boolean process(URL location, String content) {
        if (location.getHost().matches("cs\\d+\\.divokekmeny.cz")) {

            name = location.getHost().substring(0, location.getHost().indexOf("."));
            host = location.getHost().substring(location.getHost().indexOf(".") + 1);

            return true;
        } else if (location.getHost().equals("www.twstats.com")) {

            name = location.getPath().substring(1, location.getPath().indexOf("/", 1));
            host = null;

            return true;
        } else
            return false;
    }
}
