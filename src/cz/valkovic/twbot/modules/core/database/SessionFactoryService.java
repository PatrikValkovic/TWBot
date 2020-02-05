package cz.valkovic.twbot.modules.core.database;

import org.hibernate.SessionFactory;

/**
 * Service that creates ServiceFactory for database.
 */
public interface SessionFactoryService {

    /**
     * Create session factory.
     * @return Created session factory.
     */
    SessionFactory create();

}
