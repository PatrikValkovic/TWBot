package cz.valkovic.twbot.modules.core.settings;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Allows to obtain setting.
 */
public interface SettingsProviderService {

    /**
     * Observe setting.
     * @param type Type of setting to observe.
     * @param callback Callback to call, when setting change (also get call on the first time).
     * @param <T> Type of setting.
     * @return Object that can be later used to remove observer.
     * @throws IllegalArgumentException When the setting type doesn't exist.
     */
    <T> Object observe(Class<T> type, Consumer<T> callback) throws IllegalArgumentException;

    /**
     * Observe setting.
     * The callback will be called in render method.
     * @param type Type of setting to observe.
     * @param callback Callback to call, when setting change (also get call on the first time).
     * @param <T> Type of setting.
     * @return Object that can be later used to remove observer.
     * @throws IllegalArgumentException When the setting type doesn't exist.
     */
    <T> Object observeInRender(Class<T> type, Consumer<T> callback) throws IllegalArgumentException;

    /**
     * Remove observer from the setting.
     * @param o Object returned from `observe` method.
     */
    void removeObserver(Object o);

    /**
     * Allows to observe public settings only.
     * @param callback Callback to be called by observer.
     * @return Object that can be later used to delete the observer.
     */
    Object observePublicSettings(Consumer<List<Map.Entry<Class<PublicSettings>, PublicSettings>>> callback);

    /**
     * Allows to observe public settings only.
     * @param callback Callback to be called by observer.
     * @return Object that can be later used to delete the observer.
     */
    Object observePublicSettingsInRender(Consumer<List<Map.Entry<Class<PublicSettings>, PublicSettings>>> callback);

}
