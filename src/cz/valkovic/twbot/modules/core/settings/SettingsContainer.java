package cz.valkovic.twbot.modules.core.settings;

import com.google.inject.Injector;
import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.twbot.modules.core.events.instances.ApplicationCloseEvent;
import cz.valkovic.twbot.modules.core.execution.LockTimeProvider;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.observable.Observable;
import cz.valkovic.twbot.modules.core.observable.ObservableFactory;
import cz.valkovic.twbot.modules.core.settings.instances.CorePrivateSetting;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SettingsContainer implements
        SettingRegistrationService,
        SettingsProviderService,
        SettingStorageService,
        LockTimeProvider {

    private final SettingPersistingService persisting;
    private final LoggingService log;

    private final Observable<Boolean> observable;
    private final Injector injector;
    private final Map<Class<?>, Object> settingsMap = new HashMap<>(16, 0.5f);
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Inject
    public SettingsContainer(SettingPersistingService per,
                             LoggingService log,
                             ObservableFactory observFact,
                             Injector injector,
                             EventBrokerService event) {
        persisting = per;
        this.log = log;
        this.observable = observFact.Create(true);
        this.injector = injector;


        event.listenTo(ApplicationCloseEvent.class, c -> this.store());
    }

    @Override
    public void register(SettingDemand demand) {
        storeSettingInMap(demand.getPublicSetting(), loadSetting(demand.getPublicSetting()));
        storeSettingInMap(demand.getPrivateSetting(), loadSetting(demand.getPrivateSetting()));

        Class<?> stat = demand.getStaticSetting();
        if(stat != null)
            storeSettingInMap(stat, this.injector.getInstance(stat));
    }

    private StorableSettings loadSetting(Class<? extends StorableSettings> cls) {
        if(cls == null)
            return null;

        return this.persisting.load(cls);
    }

    private void storeSettingInMap(Class<?> cls, Object setting)
    {
        if(cls == null || setting == null)
            return;

        Lock write = lock.writeLock();
        write.lock();
        try {
            this.settingsMap.put(cls, setting);
        }
        finally {
            write.unlock();
        }
    }


    @Override
    public <T> Object observe(Class<T> type, Consumer<T> callback) throws IllegalArgumentException {
        return this.observable.observe(handleObserve(type, callback));
    }

    @Override
    public <T> Object observeInRender(Class<T> type, Consumer<T> callback) throws IllegalArgumentException {
        return this.observable.observeInRender(handleObserve(type, callback));
    }

    private <T> Consumer<Boolean> handleObserve(Class<T> type, Consumer<T> callback) {
        return (b) -> {
            Lock l = lock.readLock();
            l.lock();
            try {
                if(!this.settingsMap.containsKey(type)){
                    this.log.getSettings().warn(String.format(
                            "Attempt to access setting %s, that is not present.",
                            type.getCanonicalName()
                    ));
                    return;
                }
                //noinspection unchecked
                callback.accept((T)this.settingsMap.get(type));
            }
            finally {
                l.unlock();
            }
        };
    }

    @Override
    public int getLockTime() {
        Lock l = lock.readLock();
        l.lock();
        try {
            if(!this.settingsMap.containsKey(CorePrivateSetting.class)){
                this.log.getSettings().warn("CorePrivateSetting is not present, can't acquire lock time.");
                throw new IllegalArgumentException("CorePrivateSetting is not present, can't acquire lock time.");
            }
            return ((CorePrivateSetting)this.settingsMap.get(CorePrivateSetting.class)).maxLockWaitingTime();
        }
        finally {
            l.unlock();
        }
    }

    @Override
    public void removeObserver(Object o) {
        this.observable.unregister(o);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void store() {
        log.getSettings().debug("Starting storing setting");
        Lock l = lock.readLock();
        l.lock();
        try {
            settingsMap.forEach((Class<?> type, Object instance) -> {
                if(StorableSettings.class.isAssignableFrom(type))
                    this.persisting.store((Class<StorableSettings>)type, (StorableSettings)instance);
            });
        }
        finally {
            l.unlock();
        }
        log.getSettings().debug("All setting stored");
        //TODO handle change
    }
}
