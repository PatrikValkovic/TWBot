package cz.valkovic.java.twbot.services.parsers.pipes;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParallelPipe implements ParsingPipe{

    private List<ParsingPipe> pipes = new ArrayList<>();

    @Override
    public synchronized boolean process(URL location, String content) {
        return pipes.stream()
                    .allMatch(parsingPipe -> parsingPipe.process(location, content));
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
