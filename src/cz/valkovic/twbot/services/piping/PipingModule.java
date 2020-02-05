package cz.valkovic.twbot.services.piping;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import cz.valkovic.twbot.services.piping.elementary.ConditionPipe;
import cz.valkovic.twbot.services.piping.elementary.ConditionPipeFactory;
import cz.valkovic.twbot.services.piping.elementary.ParserPipe;
import cz.valkovic.twbot.services.piping.elementary.ParserPipeFactory;

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
