package cz.valkovic.java.twbot.services.configuration;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Mutable;

public interface InterConfiguration extends Mutable, Accessible {

    @DefaultValue("true")
    boolean firstRun();

    @DefaultValue("0")
    int majorVersion();

    @DefaultValue("1")
    int minorVersion();

    @DefaultValue("0")
    int patchVersion();

    default String version() {
        return majorVersion() + "." + minorVersion() + "." + patchVersion();
    }
}
