package cz.valkovic.twbot.modules.parsing.setting;

import cz.valkovic.twbot.modules.core.settings.PublicSettings;
import cz.valkovic.twbot.modules.setting.SettingDescription;

public interface ParsingPublicSetting extends PublicSettings {

    @SettingDescription("How often in milliseconds parse the website. Doesn't require reloading of the website.")
    @DefaultValue("10000")
    int parseTime();
}
