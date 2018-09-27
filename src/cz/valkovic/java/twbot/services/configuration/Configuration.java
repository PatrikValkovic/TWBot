package cz.valkovic.java.twbot.services.configuration;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Mutable;


public interface Configuration extends Mutable, Accessible {

    @DefaultValue("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134")
    String userAgent();

    @DefaultValue("10000")
    int reloadPageMin();

    @DefaultValue("20000")
    int reloadPageMax();

    @DefaultValue("1000")
    int parseTime();

    @DefaultValue("1500")
    int navigationTimeMin();

    @DefaultValue("8000")
    int navigationTimeMax();

    @DefaultValue("1280")
    int windowWidth();

    @DefaultValue("768")
    int windowHeight();

    @DefaultValue("False")
    boolean maximalized();

    @DefaultValue("False")
    boolean fullscreen();
}
