package cz.valkovic.twbot.modules.browsing.setting;

import cz.valkovic.twbot.modules.core.settings.PublicSettings;
import cz.valkovic.twbot.modules.setting.SettingDescription;

public interface BrowserPublicSetting extends PublicSettings {

    @SettingDescription("Website, that should be load on startup of the application.")
    @DefaultValue("https://www.divokekmeny.cz")
    String homepage();

    @SettingDescription("UserAgent to use, so the application will not know your browser.")
    @DefaultValue("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134")
    String userAgent();

    @SettingDescription("Minimum time in milliseconds, in which should be the page reloaded.")
    @DefaultValue("10000")
    int reloadPageMin();

    @SettingDescription("Maximum time in milliseconds, in which will be the page reloaded. Complementary to reloadPageMin and uses random value between the range.")
    @DefaultValue("20000")
    int reloadPageMax();

}
