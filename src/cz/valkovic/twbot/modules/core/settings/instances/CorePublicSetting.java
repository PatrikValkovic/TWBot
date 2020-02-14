package cz.valkovic.twbot.modules.core.settings.instances;

import cz.valkovic.twbot.modules.core.settings.PublicSettings;
import cz.valkovic.twbot.modules.setting.SettingDescription;

public interface CorePublicSetting extends PublicSettings {

    @SettingDescription("Defines the minimum time in milliseconds, that is allowed to perform action (navigate to different site or execute script).\nLeft part of the interval with actionTimeMax. Random number from this interval will be chosen.")
    @DefaultValue("1500")
    int actionTimeMin();

    @SettingDescription("Defines the maximum time in milliseconds, that is allowed to perform action (navigate to different site or execute script).\nRight part of the interval with actionTimeMin. Random number from this interval will be chosen.")
    @DefaultValue("5000")
    int actionTimeMax();

    @SettingDescription("Window width on startup.")
    @DefaultValue("1280")
    int windowWidth();

    @SettingDescription("Window height on startup.")
    @DefaultValue("768")
    int windowHeight();

    @SettingDescription("To run application is maximized mode by default.")
    @DefaultValue("False")
    boolean maximized();

    @SettingDescription("To run application in fullscreen by default.")
    @DefaultValue("False")
    boolean fullscreen();

}
