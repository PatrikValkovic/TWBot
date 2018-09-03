package cz.valkovic.java.twbot.services.connectors;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import cz.valkovic.java.twbot.services.connectors.navigation.NavigationMiddleware;
import cz.valkovic.java.twbot.services.connectors.navigation.NavigationService;

public class ConnectorsModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder()
                .implement(PipeConnection.class, PipeConnectionImpl.class)
                .build(PipeConnectionFactory.class));

        this.bind(NavigationMiddleware.class).to(NavigationService.class);

    }


}
