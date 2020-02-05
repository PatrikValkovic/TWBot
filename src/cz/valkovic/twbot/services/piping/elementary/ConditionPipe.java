package cz.valkovic.twbot.services.piping.elementary;

import com.google.inject.assistedinject.Assisted;
import cz.valkovic.twbot.services.piping.ParsingPipe;
import java.net.URL;
import java.util.function.BiPredicate;
import javax.inject.Inject;

public class ConditionPipe implements ParsingPipe {

    BiPredicate<URL, String> predicate;
    ParsingPipe next;

    @Inject
    public ConditionPipe(@Assisted BiPredicate<URL, String> predicate,
                         @Assisted ParsingPipe next) {
        this.predicate = predicate;
        this.next = next;
    }

    @Override
    public boolean process(URL location, String content) throws Exception {
        if(predicate == null || !predicate.test(location, content) || next == null)
            return true;

        return next.process(location, content);
    }

    public ConditionPipe withPredicate(BiPredicate<URL, String> predicate){
        this.predicate = predicate;
        return this;
    }

    public ConditionPipe follow(ParsingPipe pipe){
        return this.andFollow(pipe);
    }

    public ConditionPipe andFollow(ParsingPipe pipe){
        this.next = pipe;
        return this;
    }
}
