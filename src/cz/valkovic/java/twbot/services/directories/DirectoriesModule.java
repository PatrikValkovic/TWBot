package cz.valkovic.java.twbot.services.directories;

import com.google.inject.AbstractModule;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

public class DirectoriesModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(AppDirs.class).toProvider(AppDirsFactory::getInstance);
        bind(DirectoriesService.class).to(AppDirsDirectoriesService.class);
    }


}
