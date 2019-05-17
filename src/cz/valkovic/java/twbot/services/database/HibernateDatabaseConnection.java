package cz.valkovic.java.twbot.services.database;


import cz.valkovic.java.twbot.services.configuration.Configuration;
import cz.valkovic.java.twbot.services.directories.DirectoriesService;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import cz.valkovic.java.twbot.services.messaging.MessageService;
import cz.valkovic.java.twbot.services.messaging.messages.ApplicationClosing;
import cz.valkovic.java.twbot.services.messaging.messages.HibernateLoaded;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

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
                                       Configuration conf,
                                       MessageService message) {
        this.dir = dir;
        this.log = log;
        this.conf = conf;
        this.message = message;
        this.factory = createFactory();

        this.message.listenTo(ApplicationClosing.class, e -> this.close());
    }

    private DirectoriesService dir;
    private LoggingService log;
    private Configuration conf;
    private SessionFactory factory;
    private MessageService message;

    private String getConnectionString() {
        return "jdbc:sqlite:" + Paths.get(this.dir.getDataDir(), "database.db").toString();
    }

    private SessionFactory createFactory() {
        log.getLoading().debug("Loading configuration for Hibernate");

        org.hibernate.cfg.Configuration hiberConf = new org.hibernate.cfg.Configuration();
        try {
            hiberConf.configure();
            hiberConf.setProperty("hibernate.connection.url", this.getConnectionString());
            if (this.conf.firstRun()) {
                log.getLoading().info("Running for first time, Hibernate will create database");
                hiberConf.setProperty("hibernate.hbm2ddl.auto", "create");
            }
            factory = hiberConf.buildSessionFactory();
        }
        catch (HibernateException e) {
            log.getLoading().error("Cannot load hibernate");
            log.getLoading().debug(e, e);
            throw e;
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
