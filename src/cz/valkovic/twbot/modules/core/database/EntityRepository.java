package cz.valkovic.twbot.modules.core.database;

import cz.valkovic.twbot.modules.core.observable.Observable;
import java.util.List;

/**
 * Interface that allows to work with entities.
 * @param <K> Key type of the entity
 * @param <T> Entity type.
 */
public interface EntityRepository<K extends Comparable<K>, T extends BaseEntity<K>> {

    /**
     * Get entity by it's primary key.
     * Entities are by default detached, so it needs to be updated explicitly.
     * @param key Primary key.
     * @return Observable entity. Null if entity with key doesn't exist.
     */
    Observable<T> find(K key);

    /**
     * Get all entities as observable list.
     * Entities are by default detached, so it needs to be updated explicitly.
     * @return All entities stored in database.
     */
    Observable<List<Observable<T>>> all();

    /**
     * Store entity into the database.
     * @param entity Entity to store.
     */
    Observable<T> store(T entity);

    /**
     * Update entity in the database.
     * @param entity Entity to update.
     */
    Observable<T> update(T entity);

    /**
     * Remove entity from the database.
     * @param entity Entity to remove.
     */
    void remove(T entity);
}
