package cz.valkovic.java.twbot.services.parsers.pipes;

import com.google.inject.AbstractModule;

public class PipesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ParsingPipe.class).to(InitPipe.class);
        bind(ServerInformationProvider.class).to(ServernameExtractorPipe.class);
    }



}
