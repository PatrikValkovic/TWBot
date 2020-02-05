package cz.valkovic.java.twbot.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import cz.valkovic.java.twbot.modules.browsing.BrowsingModule;
import cz.valkovic.java.twbot.modules.core.CoreModule;
import cz.valkovic.java.twbot.modules.parsing.ParsingModule;
import cz.valkovic.java.twbot.services.ServicesModule;

public class ModulesLoader extends AbstractModule {

    private static Injector injector;

    public static Injector getInjector(){
        if(injector == null){
            injector = Guice.createInjector(new ModulesLoader());
        }
        return injector;
    }

    @Override
    protected void configure() {
        this.install(new CoreModule());
        this.install(new BrowsingModule());
        this.install(new ParsingModule());

        this.install(new ServicesModule());
    }
}
