package cz.valkovic.twbot.modules.core.database;

import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.observable.Observable;
import cz.valkovic.twbot.modules.core.observable.ObservableFactory;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class EntityRepositoryImpl<K extends Comparable<K>, T extends BaseEntity<K>>
        implements EntityRepository<K, T> {

    private final Class<T> entity;
    private final DatabaseConnectionService database;
    private final ObservableFactory observableFactory;
    private final LoggingService log;
    private final Map<K, Observable<T>> cache = new TreeMap<>();
    private final Observable<List<Observable<T>>> all;

    @Inject
    public EntityRepositoryImpl(
            Class<T> entity,
            DatabaseConnectionService database,
            ObservableFactory observableFactory,
            LoggingService log
    ) {
        this.entity = entity;
        this.database = database;
        this.observableFactory = observableFactory;
        this.log = log;

        this.all = observableFactory.create(new ArrayList<>());
    }


    @Override
    public Observable<T> find(K key) {
        if (this.cache.containsKey(key))
            return this.cache.get(key);

        T ent = this.database.entityManagerNoTransaction(em -> {
            return em.find(this.entity, key);
        });
        if (ent == null)
            return null;

        this.database.getEntityManager().detach(ent);
        Observable<T> observEntity = this.observableFactory.create(ent);
        this.cache.put(ent.getId(), observEntity);
        if (this.all.getValue().stream().noneMatch(e -> e.getValue().getId() == ent.getId())) {
            this.all.getValue().add(observEntity);
            this.all.setValue(this.all.getValue());
        }
        return observEntity;
    }

    @Override
    public Observable<List<Observable<T>>> all() {
        CriteriaBuilder cb = this.database.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(this.entity);
        Root<T> rootEntry = cq.from(this.entity);
        CriteriaQuery<T> sel = cq.select(rootEntry);
        TypedQuery<T> allQuery = this.database.getEntityManager().createQuery(sel);

        for (T entity : allQuery.getResultList()) {
            this.find(entity.getId());
        }
        return this.all;
    }

    @Override
    public Observable<T> store(T entity) {
        try {
            this.database.entityManager(em -> {
                em.persist(entity);
            });
        }
        catch (EntityExistsException e) {
            this.log.getDatabase().warn(String.format(
                    "Can't store entity %s because it is already in database %s",
                    entity.getId(),
                    this.entity.getCanonicalName()
            ));
            return null;
        }

        return this.find(entity.getId());
    }

    @Override
    public Observable<T> update(T entity) {
        T updated = this.database.entityManager(em -> {
            T ent = em.find(this.entity, entity.getId());
            if (ent == null)
                return null;
            return em.merge(entity);
        });

        if (updated == null) {
            this.log.getDatabase().warn(String.format(
                    "Couldn't update entity with id %s: %s",
                    entity.getId(),
                    this.entity.getCanonicalName()
            ));
            return null;
        }

        return this.find(updated.getId());
    }

    @Override
    public void remove(T entity) {
        boolean removed = this.database.entityManager(em -> {
            T ent = em.find(this.entity, entity.getId());
            if (ent != null)
                em.remove(entity);
            return ent != null;
        });

        if (!removed) {
            this.log.getDatabase().warn(String.format(
                    "Entity with id %s can't be removed: %s",
                    entity.getId(),
                    this.entity.getCanonicalName()
            ));
        }

        if (this.cache.containsKey(entity.getId())) {
            this.cache.get(entity.getId()).setValue(null);
            this.cache.remove(entity.getId());
        }
        int index = IntStream.range(0, this.all.getValue().size())
                             .filter(i -> this.all.getValue().get(i).getValue() == null ||
                                     this.all.getValue().get(i).getValue().getId() == entity.getId())
                             .findFirst().orElse(-1);
        if(index != -1){
            this.all.getValue().remove(index);
            this.all.setValue(this.all.getValue());
        }
    }
}
