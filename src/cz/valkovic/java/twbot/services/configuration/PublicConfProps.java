package cz.valkovic.java.twbot.services.configuration;

import org.aeonbits.owner.Config;

public interface PublicConfProps {

    @Config.DefaultValue("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134")
    String userAgent();

    @Config.DefaultValue("10000")
    int reloadPageMin();

    @Config.DefaultValue("20000")
    int reloadPageMax();

    @Config.DefaultValue("1000")
    int parseTime();

    @Config.DefaultValue("1500")
    int navigationTimeMin();

    @Config.DefaultValue("8000")
    int navigationTimeMax();

    @Config.DefaultValue("1280")
    int windowWidth();

    @Config.DefaultValue("768")
    int windowHeight();

    @Config.DefaultValue("False")
    boolean maximalized();

    @Config.DefaultValue("False")
    boolean fullscreen();

    String username();

    String password();

    String serverName();

    @Config.DefaultValue("https://www.divokekmeny.cz")
    String homepage();
}
