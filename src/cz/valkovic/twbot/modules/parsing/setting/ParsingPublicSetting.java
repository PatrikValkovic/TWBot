package cz.valkovic.twbot.modules.parsing.setting;

import cz.valkovic.twbot.modules.core.settings.PublicSettings;
import cz.valkovic.twbot.modules.setting.SettingDescription;

public interface ParsingPublicSetting extends PublicSettings {

    @SettingDescription("How often in milliseconds parse the website. Doesn't require reloading of the website.")
    @DefaultValue("10000")
    int parseTime();

    @SettingDescription("Your username, that can be used to log in. Please note that your username and password is stored unsecurely on this computer.")
    String username();

    @SettingDescription("Your password, that can be used to log in. Please note that your username and password is stored unsecurely on this computer!")
    String password();

    @SettingDescription("Default server, that you want to log in by default. Leave empty if you want to choose server yourself.")
    String serverName();
}
