package cz.valkovic.java.twbot.modules.core.settings;

/**
 * Service that allows registration of module settings.
 */
public interface SettingRegistrationService {

    /**
     * Register setting of the module.
     * @param demand Class containing all the settings the module defines.
     */
    void register(SettingDemand demand);

}
