package cz.valkovic.java.twbot.services.piping;

import cz.valkovic.java.twbot.services.parsers.TWStatsSettingParser;
import cz.valkovic.java.twbot.services.parsers.TWStatsUnitParser;
import cz.valkovic.java.twbot.services.piping.elementary.FalsePipe;
import cz.valkovic.java.twbot.services.piping.elementary.ParallelPipe;
import cz.valkovic.java.twbot.services.piping.elementary.ParserPipeFactory;
import cz.valkovic.java.twbot.services.piping.elementary.SeriesPipe;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.net.URL;

@Singleton
public class InitPipe implements ParsingPipe {

    private ParsingPipe pipe = null;

    @Inject
    public void init(Provider<SeriesPipe> series,
                     Provider<FalsePipe> falsePipe,
                     Provider<ParallelPipe> paralel,
                     Provider<ShouldParsePipe> shouldParse,
                     Provider<TWStatsConfigurationPipe> twStatConfiguration,
                     Provider<ServernameExtractorPipe> servernameExtractor,
                     Provider<TWstatsConditionPipe> TWstatsCondition,
                     Provider<AppConditionPipe> appCondition,
                     ParserPipeFactory parserPipeFactory,

                     Provider<TWStatsSettingParser> twstatsSettingParser,
                     Provider<TWStatsUnitParser> twStatsUnitParser) {

        ParsingPipe twstatsParsing = paralel.get()
                                            .add(parserPipeFactory.create(twstatsSettingParser.get()))
                                            .add(parserPipeFactory.create(twStatsUnitParser.get()));
        ParsingPipe appParsing = paralel.get()
                                        .add(falsePipe.get())
                                        .add(falsePipe.get());

        pipe = series.get()
                     .add(shouldParse.get())
                     .add(servernameExtractor.get())
                     .add(twStatConfiguration.get())
                     .add(paralel.get()
                                 .add(TWstatsCondition.get().andFollow(twstatsParsing))
                                 .add(appCondition.get().andFollow(appParsing)));
    }

    @Override
    public boolean process(URL location, String content) {
        return pipe.process(location, content);
    }
}
