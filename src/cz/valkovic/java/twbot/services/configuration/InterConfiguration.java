package cz.valkovic.java.twbot.services.configuration;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Mutable;

public interface InterConfiguration extends Mutable, Accessible {

    @DefaultValue("true")
    boolean firstRun();

}
