package cz.valkovic.java.twbot.services.database;


import cz.valkovic.java.twbot.services.configuration.InterConfiguration;
import cz.valkovic.java.twbot.services.directories.DirectoriesService;
import cz.valkovic.java.twbot.services.logging.ExitWrapper;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import cz.valkovic.java.twbot.services.messaging.MessageService;
import cz.valkovic.java.twbot.services.messaging.messages.ApplicationClosing;
import cz.valkovic.java.twbot.services.messaging.messages.HibernateLoaded;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import java.io.Closeable;
import java.nio.file.Paths;

@Singleton
public class HibernateDatabaseConnection implements DatabaseConnection, Closeable {


    @Inject
    public HibernateDatabaseConnection(DirectoriesService dir,
                                       LoggingService log,
                                       InterConfiguration interConf,
                                       MessageService message) {
        this.dir = dir;
        this.log = log;
        this.interConf = interConf;
        this.message = message;
        this.factory = createFactory();

        this.message.listenTo(ApplicationClosing.class, e -> this.close());
    }

    private DirectoriesService dir;
    private LoggingService log;
    private InterConfiguration interConf;
    private SessionFactory factory;
    private MessageService message;

    private String getConnectionString() {
        return "jdbc:sqlite:" + Paths.get(this.dir.getDataDir(), "database.db").toString();
    }

    private SessionFactory createFactory() {
        log.getLoading().debug("Loading configuration for Hibernate");

        Configuration conf = new Configuration();
        try {
            conf.configure();
            conf.setProperty("hibernate.connection.url", this.getConnectionString());
            if (interConf.firstRun()) {
                log.getLoading().info("Running for first time, Hibernate will create database");
                conf.setProperty("hibernate.hbm2ddl.auto", "create");
            }
            factory = conf.buildSessionFactory();
        }
        catch (HibernateException e) {
            log.getLoading().error("Cannot load hibernate");
            log.getLoading().debug(e, e);
            new ExitWrapper().exit();
        }

        log.getLoading().debug("Hibernate configuration loaded");
        this.message.invoke(new HibernateLoaded());
        return factory;
    }

    @Override
    public synchronized EntityManager getEntityManager() {
        return this.factory.createEntityManager();
    }

    @Override
    public synchronized boolean loaded() {
        return this.factory != null;
    }

    @Override
    public synchronized void close() {
        try {
            this.log.getExit().debug("Closing database");
            this.factory.close();
            this.log.getExit().info("Database closed");
        }
        catch (HibernateException e) {
            this.log.getExit().error("Unable to close database connection");
            this.log.getExit().debug(e, e);
        }
    }
}
