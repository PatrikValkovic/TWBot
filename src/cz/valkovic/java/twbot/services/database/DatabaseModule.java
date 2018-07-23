package cz.valkovic.java.twbot.services.database;

import com.google.inject.AbstractModule;

public class DatabaseModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DatabaseConnection.class).to(HibernateDatabaseConnection.class);
    }
}
