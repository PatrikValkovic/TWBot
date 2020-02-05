package cz.valkovic.twbot;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import cz.valkovic.twbot.modules.core.importing.TWModule;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;
import org.reflections.Reflections;

/**
 * Registers modules to the application.
 */
public class ModulesRegistration {

    private static LinkedList<AbstractModule> modules() {
        Reflections reflect = new Reflections("cz.valkovic.twbot");
        return reflect.getTypesAnnotatedWith(TWModule.class)
                      .stream()
                      .map(c -> {
                          try {
                              return c.getConstructor().newInstance();
                          }
                          catch (Exception e) {
                              e.printStackTrace();
                          }
                          return null;
                      })
                      .filter(Objects::nonNull)
                      .map(m -> (AbstractModule) m)
                      .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Creates DI injector.
     *
     * @return Injector.
     */
    public static Injector createInjector() {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                modules().forEach(this::install);
            }
        });
    }

}
