package cz.valkovic.java.twbot.services.connectors;

import cz.valkovic.java.twbot.services.parsers.pipes.ParsingPipe;

public interface PipeConnection {

    void setParsingPipe(ParsingPipe pipe);

}
