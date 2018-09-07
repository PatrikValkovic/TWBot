package cz.valkovic.java.twbot.services.piping.elementary;

import com.google.inject.assistedinject.Assisted;
import cz.valkovic.java.twbot.services.parsers.Parser;
import cz.valkovic.java.twbot.services.piping.ParsingPipe;
import org.jsoup.Jsoup;

import javax.inject.Inject;
import java.net.URL;

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
