package cz.valkovic.twbot.modules.core.database;

/**
 * Base class for all the entities that should be used with the repository.
 * @param <T> Type of the id.
 */
public interface BaseEntity<T> {

    /**
     * Returns ID of the entity.
     * @return ID of the entity.
     */
    T getId();

}
