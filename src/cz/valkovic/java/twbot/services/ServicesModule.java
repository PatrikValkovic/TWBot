package cz.valkovic.java.twbot.services;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import cz.valkovic.java.twbot.services.logging.LoggingModule;

public class ServicesModule extends AbstractModule {

    private static Injector injector;

    public static Injector getInjector(){
        if(injector == null){
            injector = Guice.createInjector(new ServicesModule());
        }
        return injector;
    }

    @Override
    protected void configure() {
        install(new LoggingModule());
        requestStaticInjection(ResourceLoaderService.class);
    }
}
