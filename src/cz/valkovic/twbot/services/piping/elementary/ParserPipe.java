package cz.valkovic.twbot.services.piping.elementary;

import com.google.inject.assistedinject.Assisted;
import cz.valkovic.twbot.services.parsers.Parser;
import cz.valkovic.twbot.services.piping.ParsingPipe;
import java.net.URL;
import javax.inject.Inject;
import org.jsoup.Jsoup;

public class ParserPipe implements ParsingPipe {

    private Parser parser;

    @Inject
    public ParserPipe(@Assisted Parser parser) {
        this.parser = parser;
    }

    @Override
    public boolean process(URL location, String content) {
        boolean shouldParse = parser.willProccess(location);

        if(shouldParse)
            parser.proccess(location, Jsoup.parse(content));

        return shouldParse;
    }
}
