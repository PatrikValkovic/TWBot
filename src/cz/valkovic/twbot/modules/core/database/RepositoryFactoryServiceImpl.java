package cz.valkovic.twbot.modules.core.database;

import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.observable.ObservableFactory;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class RepositoryFactoryServiceImpl implements RepositoryFactoryService {

    private final ObservableFactory observableFactory;
    private final LoggingService log;
    private final DatabaseConnectionService database;

    private final Map<Class<?>, EntityRepository<?,?>> repos = new HashMap<>(16, 0.5f);

    @Inject
    public RepositoryFactoryServiceImpl(
            ObservableFactory observableFactory,
            LoggingService log,
            DatabaseConnectionService database
    ) {
        this.observableFactory = observableFactory;
        this.log = log;
        this.database = database;
    }

    @Override
    public <K extends Comparable<K>, T extends BaseEntity<K>> EntityRepository<K, T> createRepository(Class<T> entity) {
        if(!this.repos.containsKey(entity))
            this.repos.put(entity, new EntityRepositoryImpl<>(entity, database, observableFactory, log));
        //noinspection unchecked
        return (EntityRepository<K, T>)this.repos.get(entity);
    }
}
