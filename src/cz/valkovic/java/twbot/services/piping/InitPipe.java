package cz.valkovic.java.twbot.services.piping;

import cz.valkovic.java.twbot.services.parsers.TWStatsBuildingsParser;
import cz.valkovic.java.twbot.services.parsers.TWStatsSettingParser;
import cz.valkovic.java.twbot.services.parsers.TWStatsUnitParser;
import cz.valkovic.java.twbot.services.piping.elementary.*;

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
                     Provider<ThreadedPipe> threaded,
                     Provider<ShouldParsePipe> shouldParse,
                     Provider<TWStatsConfigurationPipe> twStatConfiguration,
                     Provider<ServernameExtractorPipe> servernameExtractor,
                     Provider<TWstatsConditionPipe> TWstatsCondition,
                     Provider<AppConditionPipe> appCondition,
                     Provider<LoginPipe> login,
                     ParserPipeFactory parserPipeFactory,

                     Provider<TWStatsSettingParser> twstatsSettingParser,
                     Provider<TWStatsUnitParser> twStatsUnitParser,
                     Provider<TWStatsBuildingsParser> twStatsBuildingParser) {

        ParsingPipe twstatsParsing = paralel.get()
                                            .add(parserPipeFactory.create(twstatsSettingParser.get()))
                                            .add(parserPipeFactory.create(twStatsUnitParser.get()))
                                            .add(parserPipeFactory.create(twStatsBuildingParser.get()));
        ParsingPipe appParsing = paralel.get()
                                        .add(falsePipe.get())
                                        .add(falsePipe.get());

        ParsingPipe beginning = series.get()
                                      .add(login.get())
                                      .add(shouldParse.get())
                                      .add(servernameExtractor.get())
                                      .add(twStatConfiguration.get())
                                      .add(paralel.get()
                                                  .add(TWstatsCondition.get().andFollow(twstatsParsing))
                                                  .add(appCondition.get().andFollow(appParsing)));

        pipe = series.get()
                     .add(threaded.get()
                                  .to(beginning));
    }

    @Override
    public boolean process(URL location, String content) throws Exception {
        return this.pipe.process(location, content);
    }
}
