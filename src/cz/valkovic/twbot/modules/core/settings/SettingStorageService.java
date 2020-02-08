package cz.valkovic.twbot.modules.core.settings;

/**
 * Interface that allow storing every setting.
 */
public interface SettingStorageService {

    /**
     * Stores all setting into the files and notify application about it.
     */
    void store();

}
