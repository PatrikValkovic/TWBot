package cz.valkovic.twbot.modules.browsing.setting;

import cz.valkovic.twbot.modules.core.settings.PublicSettings;
import cz.valkovic.twbot.modules.setting.SettingDescription;

public interface BrowserPublicSetting extends PublicSettings {

    @SettingDescription("Website, that should be load on startup of the application.")
    @DefaultValue("https://www.divokekmeny.cz")
    String homepage();

}
