package cz.valkovic.java.twbot.services.database;

import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.io.Closeable;
import java.io.IOException;


public interface DatabaseConnection extends Closeable {

    Session getSession();

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
