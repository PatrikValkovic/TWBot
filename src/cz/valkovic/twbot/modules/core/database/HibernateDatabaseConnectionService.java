package cz.valkovic.twbot.modules.core.database;


import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.twbot.modules.core.events.instances.ApplicationCloseEvent;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import java.io.Closeable;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import org.hibernate.HibernateException;

@Singleton
public class HibernateDatabaseConnectionService implements DatabaseConnectionService, Closeable {

    private final LoggingService log;
    private final SessionFactoryService factory;

    @Inject
    public HibernateDatabaseConnectionService(LoggingService log,
                                              EventBrokerService events,
                                              SessionFactoryService factory) {
        this.log = log;
        this.factory = factory;
        events.listenTo(ApplicationCloseEvent.class, e -> this.close());
    }

    @Override
    public synchronized EntityManager getEntityManager() {
        return this.factory.create().createEntityManager();
    }

    @Override
    public synchronized boolean loaded() {
        return this.factory != null;
    }

    @Override
    public synchronized void close() {
        try {
            this.log.getExit().debug("Closing database");
            this.factory.create().close();
            this.log.getExit().info("Database closed");
        }
        catch (HibernateException e) {
            this.log.getExit().error("Unable to close database connection");
            this.log.getExit().debug(e, e);
        }
    }
}
