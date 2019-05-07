package cz.valkovic.java.twbot.services.piping.elementary;

import cz.valkovic.java.twbot.services.piping.ParsingPipe;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParallelPipe implements ParsingPipe {

    private List<ParsingPipe> pipes = new ArrayList<>();

    @Override
    public synchronized boolean process(URL location, String content) throws Exception {
        boolean tmp = true;
        for(ParsingPipe pipe : pipes)
            tmp = pipe.process(location, content) && tmp;
        return tmp;
    }

    public synchronized  ParallelPipe add(ParsingPipe... pipes){
        this.pipes.addAll(Arrays.asList(pipes));
        return this;
    }

    public synchronized ParallelPipe remove(ParsingPipe... pipes){
        this.pipes.removeAll(Arrays.asList(pipes));
        return this;
    }
}
