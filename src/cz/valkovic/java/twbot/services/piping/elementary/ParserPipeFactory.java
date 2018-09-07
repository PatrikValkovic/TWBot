package cz.valkovic.java.twbot.services.piping.elementary;

import cz.valkovic.java.twbot.services.parsers.Parser;

public interface ParserPipeFactory {

    ParserPipe create(Parser parser);

}
