package cz.valkovic.java.twbot.services.database;

import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.io.Closeable;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;


public interface DatabaseConnection extends Closeable {

    EntityManager getEntityManager();

    default void close_noexc(Logger l) {
        try {
            this.close();
        }
        catch (IOException e) {
            l.error("Unable to close database connection");
            l.debug(e, e);
        }
    }

    boolean loaded();


    default void entityManager(Consumer<EntityManager> callback) {
        this.entityManager(entityManager -> {
            callback.accept(entityManager);
            return 0;
        });
    }

    default <T> T entityManager(Function<EntityManager, T> callback) {
        return this.entityManagerNoTransaction(entityManager -> {
            entityManager.getTransaction().begin();
            T returned = callback.apply(entityManager);
            entityManager.getTransaction().commit();
            return returned;
        });
    }

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

    default void entityManagerNoTransaction(Consumer<EntityManager> callback) {
        this.entityManagerNoTransaction(entityManager -> {
            callback.accept(entityManager);
            return 0;
        });
    }

}
