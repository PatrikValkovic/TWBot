package cz.valkovic.twbot.modules.core;

import com.google.inject.AbstractModule;
import cz.valkovic.twbot.modules.core.database.*;
import cz.valkovic.twbot.modules.core.database.setting.DatabaseSettingDemand;
import cz.valkovic.twbot.modules.core.directories.AppDirsDirectoriesService;
import cz.valkovic.twbot.modules.core.directories.DirectoriesService;
import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.twbot.modules.core.events.EventBrokerServiceImpl;
import cz.valkovic.twbot.modules.core.execution.ExecutionService;
import cz.valkovic.twbot.modules.core.execution.ExecutionServiceImpl;
import cz.valkovic.twbot.modules.core.execution.LockTimeProvider;
import cz.valkovic.twbot.modules.core.importing.TWModule;
import cz.valkovic.twbot.modules.core.logging.Log4jLoggingService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.observable.ObservableFactory;
import cz.valkovic.twbot.modules.core.observable.ObservableFactoryImpl;
import cz.valkovic.twbot.modules.core.pipeping.*;
import cz.valkovic.twbot.modules.core.settings.*;
import cz.valkovic.twbot.modules.core.settings.instances.CoreSettingDemand;
import cz.valkovic.twbot.modules.core.tabs.*;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

@TWModule
public class CoreModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();

        // ResourceLoaderService
        bind(ResourceLoaderService.class);

        // observable
        bind(ObservableFactory.class).to(ObservableFactoryImpl.class);

        // logging
        bind(LoggingService.class).to(Log4jLoggingService.class);

        // directories
        bind(AppDirs.class).toProvider(AppDirsFactory::getInstance);
        bind(DirectoriesService.class).to(AppDirsDirectoriesService.class);

        // execution
        bind(ExecutionService.class).to(ExecutionServiceImpl.class);
        bind(LockTimeProvider.class).to(SettingsContainer.class);

        // events
        bind(EventBrokerService.class).to(EventBrokerServiceImpl.class);

        // settings
        bind(SettingPersistingService.class).to(SettingPersistingServiceImpl.class);
        bind(SettingRegistrationService.class).to(SettingsContainer.class);
        bind(SettingsProviderService.class).to(SettingsContainer.class);
        bind(SettingStorageService.class).to(SettingsContainer.class);
        requestStaticInjection(CoreSettingDemand.class);

        // database
        bind(SessionFactoryService.class).to(SessionFactoryImpl.class);
        bind(EntityRegistrationService.class).to(SessionFactoryImpl.class);
        bind(DatabaseConnectionService.class).to(HibernateDatabaseConnectionService.class);
        requestStaticInjection(DatabaseSettingDemand.class);
        requestStaticInjection(SessionFactoryImpl.class);

        // tabs
        bind(TabRegistrationService.class).to(TabsContainer.class);
        bind(TabsRetrieveService.class).to(TabsContainer.class);
        bind(LastSessionTabs.class).to(LastSessionTabsImpl.class);

        // pipeping
        bind(PipesRegistrationService.class).to(PipesContainer.class);
        bind(PipesRetrieveService.class).to(PipesContainer.class);
        bind(ParsingService.class).to(ParsingServiceImpl.class);
        requestStaticInjection(PipesSettingDemand.class);
    }
}
