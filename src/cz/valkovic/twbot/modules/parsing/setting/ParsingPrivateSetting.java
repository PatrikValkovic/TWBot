package cz.valkovic.twbot.modules.parsing.setting;

import cz.valkovic.twbot.modules.core.settings.StorableSettings;
import org.aeonbits.owner.Config;

public interface ParsingPrivateSetting extends StorableSettings {

    @Config.DefaultValue("cs\\d+\\.divokekmeny.cz")
    String ingameRegex();

    @Config.DefaultValue("divokekmeny.cz")
    String applicationDomain();

}
