package cz.valkovic.java.twbot.services.connectors;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import cz.valkovic.java.twbot.services.parsers.pipes.EmptyPipe;
import cz.valkovic.java.twbot.services.parsers.pipes.ParsingPipe;

public class ConnectorsModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder()
                .implement(PipeConnection.class, PipeConnectionImpl.class)
                .build(PipeConnectionFactory.class));

        //TODO change
        bind(ParsingPipe.class).toInstance(new EmptyPipe());

    }


}
