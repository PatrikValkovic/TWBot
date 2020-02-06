package cz.valkovic.twbot.modules.core.tabs;

import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Node;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Singleton
public class TabsContainer implements TabRegistrationService, TabsRetrieveService {

    @AllArgsConstructor
    private static class TabRecord {
        @Getter
        private String title;
        @Getter
        private Class<? extends Node> control;
        @Getter
        private boolean closable;
    }

    private Map<String, TabRecord> tabs = new HashMap<>(16, 0.5f);

    private final LoggingService log;
    private final EventBrokerService event;

    @Inject
    public TabsContainer(LoggingService log, EventBrokerService event) {
        this.log = log;
        this.event = event;
    }

    @Override
    public void register(String title, Class<? extends Node> control, boolean closable) {
        this.log.getStartup().debug(String.format(
                "Tab %s (representative by %s) registered.",
                title,
                control.getCanonicalName()
        ));
        this.tabs.put(title, new TabRecord(title, control, closable));
        this.event.invoke(new TabRegisteredEvent(title, control));
    }

    @Override
    public String[] tabsNames() {
        return this.tabs
                .values()
                .stream()
                .filter(r -> r.closable)
                .map(r -> r.title)
                .toArray(String[]::new);
    }

    @Override
    public String[] requiredTabsNames() {
        return this.tabs
                .values()
                .stream()
                .filter(r -> !r.closable)
                .map(r -> r.title)
                .toArray(String[]::new);
    }

    @Override
    public Class<? extends Node> getRepresentativeClass(String name) {
        return this.tabs.get(name).control;
    }

    @Override
    public boolean isClosable(String name) {
        return this.tabs.get(name).closable;
    }
}
