package cz.valkovic.java.twbot.services.piping;

import cz.valkovic.java.twbot.services.parsers.TWStatsBuildingsParser;
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

        pipe = series.get()
                     .add(login.get())
                     .add(shouldParse.get())
                     .add(servernameExtractor.get())
                     .add(twStatConfiguration.get())
                     .add(paralel.get()
                                 .add(TWstatsCondition.get().andFollow(twstatsParsing))
                                 .add(appCondition.get().andFollow(appParsing)));
    }

    private static class PipingClass implements Runnable {
        private boolean result;

        private URL location;
        String content;
        ParsingPipe pipe;

        public PipingClass(URL location, String content, ParsingPipe pipe) {
            this.location = location;
            this.content = content;
            this.pipe = pipe;
        }

        @Override
        public void run() {
            try {
                result = pipe.process(location, content);
            }
            catch (Exception e) {
                //TODO handle
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean process(URL location, String content) {
        Boolean value;
        PipingClass p = new PipingClass(location, content, pipe);
        Thread th = new Thread(p, "Piping thread");
        th.setDaemon(true);
        th.start();
        return true;
    }
}
