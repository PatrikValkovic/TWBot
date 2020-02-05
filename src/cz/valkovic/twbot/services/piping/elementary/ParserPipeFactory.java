package cz.valkovic.twbot.services.piping.elementary;

import cz.valkovic.twbot.services.parsers.Parser;

public interface ParserPipeFactory {

    ParserPipe create(Parser parser);

}
