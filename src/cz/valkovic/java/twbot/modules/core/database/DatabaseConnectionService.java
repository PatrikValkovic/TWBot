package cz.valkovic.java.twbot.modules.core.database;

import java.io.Closeable;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.persistence.EntityManager;

/**
 * Allows to obtain {@link EntityManager} or execute function with {@link EntityManager}.
 */
public interface DatabaseConnectionService extends Closeable {

    /**
     * Returns {@link EntityManager}
     * @return EntityManager
     */
    EntityManager getEntityManager();

    /**
     * Check whether the {@link EntityManager} was already loaded.
     * @return True if {@link EntityManager} was loaded, false otherwise.
     */
    boolean loaded();


    /**
     * Allows to execute function with {@link EntityManager}.
     * The callback is call within transaction.
     * @param callback Function to execute.
     */
    default void entityManager(Consumer<EntityManager> callback) {
        this.entityManager(entityManager -> {
            callback.accept(entityManager);
            return 0;
        });
    }

    /**
     * Allows to execute function with {@link EntityManager} and return callback's return value.
     * The callback is call within transaction.
     * @param callback Callback to call on {@link EntityManager}.
     * @param <T> Return type of callback.
     * @return Value from the callback.
     */
    default <T> T entityManager(Function<EntityManager, T> callback) {
        return this.entityManagerNoTransaction(entityManager -> {
            entityManager.getTransaction().begin();
            T returned = callback.apply(entityManager);
            entityManager.getTransaction().commit();
            return returned;
        });
    }

    /**
     * Allows to execute function with {@link EntityManager} and return callback's return value.
     * The callback is NOT call within transaction.
     * @param callback Callback to call on {@link EntityManager}.
     * @param <T> Return type of callback.
     * @return Value from the callback.
     */
    default <T> T entityManagerNoTransaction(Function<EntityManager, T> callback) {
        EntityManager mng = null;
        try {
            mng = this.getEntityManager();
            return callback.apply(mng);
        }
        finally {
            if (mng != null)
                mng.close();
        }
    }

    /**
     * Allows to execute function with {@link EntityManager}.
     * The callback is NOT call within transaction.
     * @param callback Function to execute.
     */
    default void entityManagerNoTransaction(Consumer<EntityManager> callback) {
        this.entityManagerNoTransaction(entityManager -> {
            callback.accept(entityManager);
            return 0;
        });
    }

}
