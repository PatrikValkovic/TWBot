package cz.valkovic.java.twbot.modules.core.settings;

/**
 * Class that needs to be inherited from in order to register custom settings for module.
 * You can override any method.
 */
public class SettingDemand {

    /**
     * Get settings that should be publicly available for the user to set up.
     * @return Class representing that setting.
     */
    public Class<? extends StorableSettings> getPublicSetting() {
        return null;
    }

    /**
     * Get settings that shouldn't be publicly available for the user to set up, but sometimes make reason to change it.
     * @return Class representing that setting.
     */
    public Class<? extends StorableSettings> getPrivateSetting() {
        return null;
    }

    /**
     * Get setting that is not changed at all (constants).
     * You can use dependency injection.
     */
    public Class<?> getStaticSetting() {
        return null;
    }
}
