package cz.valkovic.java.twbot.services.piping.elementary;

import cz.valkovic.java.twbot.services.piping.ParsingPipe;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SeriesPipe implements ParsingPipe {

    private List<ParsingPipe> pipes = new ArrayList<>();

    @Override
    public boolean process(URL location, String content) throws Exception {
        for (ParsingPipe p : pipes)
            if (!p.process(location, content))
                return false;
        return true;

    }

    public SeriesPipe add(ParsingPipe... pipes) {
        this.pipes.addAll(Arrays.asList(pipes));
        return this;
    }

    public SeriesPipe remove(ParsingPipe... pipes){
        this.pipes.removeAll(Arrays.asList(pipes));
        return this;
    }
}
