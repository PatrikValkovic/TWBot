package cz.valkovic.twbot.services.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class ConfigurationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ConfigurationService.class).to(OwnerConfigurationService.class);
        bind(PublicConfiguration.class).toProvider(new Provider<PublicConfiguration>() {

            @Inject
            private ConfigurationService confService;

            @Override
            public PublicConfiguration get() {
                return this.confService.getPublicConfiguration();
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
        bind(Configuration.class).to(ConfigurationImpl.class);
    }
}
