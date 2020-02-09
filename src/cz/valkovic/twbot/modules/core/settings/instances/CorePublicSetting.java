package cz.valkovic.twbot.modules.core.settings.instances;

import cz.valkovic.twbot.modules.core.settings.PublicSettings;
import cz.valkovic.twbot.modules.setting.SettingDescription;

public interface CorePublicSetting extends PublicSettings {

    @SettingDescription("UserAgent to use, so the application will not know your browser.")
    @DefaultValue("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134")
    String userAgent();

    @SettingDescription("Minimum time in milliseconds, in which should be the page reloaded.")
    @DefaultValue("10000")
    int reloadPageMin();

    @SettingDescription("Maximum time in milliseconds, in which will be the page reloaded. Complementary to reloadPageMin and uses random value between the range.")
    @DefaultValue("20000")
    int reloadPageMax();

    @SettingDescription("How often in milliseconds parse the website. Doesn't require reloading of the website.")
    @DefaultValue("1000")
    int parseTime();

    @SettingDescription("Defines the minimum time in milliseconds, that is allowed to perform action (navigate to different site or execute script).\nLeft part of the interval with actionTimeMax. Random number from this interval will be chosen.")
    @DefaultValue("1500")
    int actionTimeMin();

    @SettingDescription("Defines the maximum time in milliseconds, that is allowed to perform action (navigate to different site or execute script).\nRight part of the interval with actionTimeMin. Random number from this interval will be chosen.")
    @DefaultValue("8000")
    int actionTimeMax();

    @SettingDescription("Window width on startup.")
    @DefaultValue("1280")
    int windowWidth();

    @SettingDescription("Window height on startup.")
    @DefaultValue("768")
    int windowHeight();

    @SettingDescription("To run application is maximized mode by default.")
    @DefaultValue("False")
    boolean maximalized();

    @SettingDescription("To run application in fullscreen by default.")
    @DefaultValue("False")
    boolean fullscreen();

    //TODO move away
    @SettingDescription("Your username, that can be used to log in. Please note that your username and password is stored unsecurely on this computer.")
    String username();

    @SettingDescription("Your password, that can be used to log in. Please note that your username and password is stored unsecurely on this computer!")
    String password();

    @SettingDescription("Default server, that you want to log in by default. Leave empty if you want to choose server yourself.")
    String serverName();
}
