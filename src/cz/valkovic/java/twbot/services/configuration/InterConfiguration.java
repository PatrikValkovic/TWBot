package cz.valkovic.java.twbot.services.configuration;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Mutable;

import java.util.Random;

public interface InterConfiguration extends Mutable, Accessible {

    @DefaultValue("true")
    boolean firstRun();

    @DefaultValue("0")
    int majorVersion();

    @DefaultValue("1")
    int minorVersion();

    @DefaultValue("0")
    int patchVersion();

    @DefaultValue("cs\\d+\\.divokekmeny.cz")
    String appDomainRegex();

    @DefaultValue("www.twstats.com")
    String twstatsDomain();

    default String version() {
        return majorVersion() + "." + minorVersion() + "." + patchVersion();
    }

    default long seed() {
        Random rand = new Random();
        return rand.nextLong();
    }
}
