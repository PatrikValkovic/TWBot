package cz.valkovic.java.twbot.services.configuration;

import java.util.Random;

public interface Configuration extends
        PublicConfProps,
        InterConfProps {

    int majorVersion();

    int minorVersion();

    int patchVersion();

    default String version() {
        return majorVersion() + "." + minorVersion() + "." + patchVersion();
    }

    default long seed() {
        Random rand = new Random();
        return rand.nextLong();
    }

}
