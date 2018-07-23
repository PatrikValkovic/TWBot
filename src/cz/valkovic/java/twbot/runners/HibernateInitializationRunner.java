package cz.valkovic.java.twbot.runners;

import com.google.inject.Inject;
import com.google.inject.Injector;
import cz.valkovic.java.twbot.services.database.DatabaseConnection;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import org.hibernate.Session;

public class HibernateInitializationRunner implements Runnable {

    @Inject
    private LoggingService log;

    @Inject
    private DatabaseConnection con;

    @Override
    public void run() {
        log.getLoading().info("Loading hibernate configuration");
        try(Session s = con.getSession()){}
        log.getLoading().info("Hibernate configuration loading finished");
    }

    public static void runInSeparateThread(Injector i){
        Thread t = new Thread(i.getInstance(HibernateInitializationRunner.class));
        t.setDaemon(true);
        t.start();
    }
}
