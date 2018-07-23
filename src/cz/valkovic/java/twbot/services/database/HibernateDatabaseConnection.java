package cz.valkovic.java.twbot.services.database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cz.valkovic.java.twbot.services.configuration.InterConfiguration;
import cz.valkovic.java.twbot.services.directories.DirectoriesService;
import cz.valkovic.java.twbot.services.logging.ExitWrapper;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.Closeable;
import java.nio.file.Paths;

@Singleton
public class HibernateDatabaseConnection implements DatabaseConnection, Closeable {


    @Inject
    private DirectoriesService dir;

    @Inject
    private LoggingService log;

    @Inject
    private InterConfiguration interConf;

    private SessionFactory factory;

    private String getConnectionString(){
        return "jdbc:sqlite:" + Paths.get(this.dir.getDataDir(), "database.db").toString();
    }

    private SessionFactory createFactory(){
        log.getLoading().info("Loading configuration for Hibernate");

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
        catch(HibernateException e){
            log.getLoading().error("Cannot load hibernate");
            log.getLoading().debug(e, e);
            new ExitWrapper().exit();
        }

        log.getLoading().info("Hibernate configuration loaded");
        return factory;
    }

    @Override
    public synchronized Session getSession() {
        if(!this.loaded())
            this.createFactory();
        return this.factory.openSession();
    }

    @Override
    public synchronized boolean loaded() {
        return this.factory != null;
    }

    @Override
    public synchronized void close() {
        this.factory.close();
    }
}
