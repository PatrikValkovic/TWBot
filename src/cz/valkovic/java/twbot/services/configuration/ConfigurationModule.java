package cz.valkovic.java.twbot.services.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class ConfigurationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ConfigurationService.class).to(OwnerConfigurationService.class);
        bind(Configuration.class).toProvider(new Provider<Configuration>() {

            @Inject
            private ConfigurationService confService;

            @Override
            public Configuration get() {
                return this.confService.getConfiguration();
            }
        });
        bind(InterConfiguration.class).toProvider(new Provider<InterConfiguration>() {

            @Inject
            private ConfigurationService confService;

            @Override
            public InterConfiguration get() {
                return this.confService.getInterConfiguration();
            }
        });
    }
}
