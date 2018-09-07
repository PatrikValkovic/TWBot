package cz.valkovic.java.twbot.services.piping.elementary;

import cz.valkovic.java.twbot.services.piping.ParsingPipe;

import java.net.URL;
import java.util.function.BiPredicate;

public interface ConditionPipeFactory {

    ConditionPipe create(BiPredicate<URL, String> predicate, ParsingPipe next);

}
