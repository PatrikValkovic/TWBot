package cz.valkovic.java.twbot.runners;

import com.google.inject.Inject;
import com.google.inject.Injector;
import cz.valkovic.java.twbot.services.database.DatabaseConnection;
import cz.valkovic.java.twbot.modules.core.logging.LoggingService;

import javax.persistence.EntityManager;

public class HibernateInitializationRunner implements Runnable {

    @Inject
    private LoggingService log;

    @Inject
    private DatabaseConnection con;

    @Override
    public void run() {
        log.getStartup().info("Loading hibernate configuration");
        EntityManager mng = con.getEntityManager();
        mng.close();
        log.getStartup().info("Hibernate configuration loading finished");
    }

    public static void runInSeparateThread(Injector i){
        Thread t = new Thread(i.getInstance(HibernateInitializationRunner.class));
        t.setDaemon(true);
        t.start();
    }
}
