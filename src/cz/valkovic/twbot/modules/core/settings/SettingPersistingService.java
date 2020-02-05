package cz.valkovic.twbot.modules.core.settings;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Mutable;

/**
 * Allows to store and retrieve setting into or from the configuration file.
 */
public interface SettingPersistingService {

    /**
     * Load setting from the file. The path is determined by the name of the class.
     * @param type Type of setting to load.
     * @param <T> Type of setting.
     * @return Setting loaded from file.
     */
    <T extends Mutable> T load(Class<T> type);

    /**
     * Store setting into the file.
     * @param type Type of setting to store (determine file name).
     * @param settings Setting instance to store.
     * @param <T> Type of instance to store.
     */
    <T extends Accessible> void store(Class<? extends T> type, T settings);

}
