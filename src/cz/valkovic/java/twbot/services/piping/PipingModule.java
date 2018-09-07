package cz.valkovic.java.twbot.services.piping;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import cz.valkovic.java.twbot.services.piping.elementary.ConditionPipe;
import cz.valkovic.java.twbot.services.piping.elementary.ConditionPipeFactory;
import cz.valkovic.java.twbot.services.piping.elementary.ParserPipe;
import cz.valkovic.java.twbot.services.piping.elementary.ParserPipeFactory;

public class PipingModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ParsingPipe.class).to(InitPipe.class);
        bind(ServerInformationProvider.class).to(ServernameExtractorPipe.class);

        install(new FactoryModuleBuilder()
                .implement(ConditionPipe.class, ConditionPipe.class)
                .build(ConditionPipeFactory.class));
        install(new FactoryModuleBuilder()
                .implement(ParserPipe.class, ParserPipe.class)
                .build(ParserPipeFactory.class));
    }
}
