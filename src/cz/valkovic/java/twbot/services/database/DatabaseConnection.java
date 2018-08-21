package cz.valkovic.java.twbot.services.database;

import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.io.Closeable;
import java.io.IOException;


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

}
