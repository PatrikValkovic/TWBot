package cz.valkovic.twbot.modules.core.database;

/**
 * Allows to register entities before the database is initialized.
 */
public interface EntityRegistrationService {

    /**
     * Register entity to the JPA.
     * @param entity Entity to register.
     */
    void registerEntity(Class<? extends BaseEntity<?>> entity);

}
