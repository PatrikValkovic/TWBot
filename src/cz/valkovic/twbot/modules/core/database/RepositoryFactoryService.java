package cz.valkovic.twbot.modules.core.database;

public interface RepositoryFactoryService {

    <K extends Comparable<K>, T extends BaseEntity<K>>
    EntityRepository<K, T> createRepository(Class<T> entity);

}
