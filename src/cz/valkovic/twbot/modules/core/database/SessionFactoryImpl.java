package cz.valkovic.twbot.modules.core.database;

import cz.valkovic.twbot.modules.core.database.events.EntityRegisteredEvent;
import cz.valkovic.twbot.modules.core.database.events.HibernateLoadedEvent;
import cz.valkovic.twbot.modules.core.database.setting.DatabasePrivateSetting;
import cz.valkovic.twbot.modules.core.database.setting.DatabaseStaticSetting;
import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.twbot.modules.core.execution.ExecutionService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.core.settings.instances.CorePrivateSetting;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class SessionFactoryImpl implements
        SessionFactoryService, EntityRegistrationService {

    private final LoggingService log;
    private final EventBrokerService event;

    private DatabaseStaticSetting dbStatic;
    private DatabasePrivateSetting dbPrivate;
    private CorePrivateSetting coreSetting;

    private final List<Class<? extends BaseEntity<?>>> entities = new LinkedList<>();
    private SessionFactory factory = null;

    @Inject
    public SessionFactoryImpl(
            LoggingService log,
            SettingsProviderService settings,
            EventBrokerService event
    ) {
        this.log = log;
        this.event = event;

        settings.observe(DatabaseStaticSetting.class, s -> this.dbStatic = s);
        settings.observe(DatabasePrivateSetting.class, s -> this.dbPrivate = s);
        settings.observe(CorePrivateSetting.class, s -> this.coreSetting = s);
    }

    /**
     * Create factory in advance.
     */
    @Inject
    public static void createFactory(LoggingService log, SessionFactoryService factory, ExecutionService exe){
        log.getDatabase().debug("Loading hibernate configuration");
        exe.run(() -> {
            factory.create();
            log.getStartup().info("Hibernate configuration loaded");
        });
    }

    @Override
    public SessionFactory create() {
        if(this.factory != null)
            return this.factory;

        log.getDatabase().debug("Loading configuration for Hibernate");

        org.hibernate.cfg.Configuration hiberConf = new org.hibernate.cfg.Configuration();
        try {
            hiberConf.configure();
            hiberConf.setProperty("hibernate.connection.url", this.dbStatic.connectionString());
            hiberConf.setProperty("hibernate.hbm2ddl.auto", this.dbPrivate.hbm2ddl());
            for(Class<? extends BaseEntity<?>> entity : this.entities)
                hiberConf.addAnnotatedClass(entity);

            if (this.coreSetting.firstRun()) {
                log.getDatabase().info("Running for first time, Hibernate will create database.");
                hiberConf.setProperty("hibernate.hbm2ddl.auto", "create");
            }
            this.factory = hiberConf.buildSessionFactory();
        }
        catch (HibernateException e) {
            log.getDatabase().error("Can't load hibernate");
            log.getDatabase().debug(e, e);
            throw e;
        }

        log.getDatabase().debug("Hibernate configuration loaded");
        this.event.invoke(new HibernateLoadedEvent());
        return factory;
    }

    @Override
    public void registerEntity(Class<? extends BaseEntity<?>> entity) {
        this.log.getDatabase().debug(String.format(
                "Entity %s registration",
                entity.getCanonicalName()
        ));
        this.entities.add(entity);
        this.event.invoke(new EntityRegisteredEvent(entity));
    }
}
