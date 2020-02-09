package cz.valkovic.twbot.services.messaging;

import cz.valkovic.twbot.modules.core.events.Event;
import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.twbot.modules.core.events.Listener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBrokerServiceTestImpl implements EventBrokerService {

    private Map<Class<? extends Event>, List<Listener<? extends Event>>> messages = new HashMap<>();

    @Override
    public <e extends Event> EventBrokerServiceTestImpl listenTo(Class<e> listenTo, Listener<e> listener) {
        if (!this.messages.containsKey(listenTo))
            this.messages.put(listenTo, new ArrayList<>());
        this.messages.get(listenTo).add(listener);
        return this;
    }

    /**
     * Remove listener from the event.
     *
     * @param o Value returned from the `listenTo` method.
     * @return Instance self.
     */
    @Override
    public EventBrokerService remove(Object o) {
        return this;
    }

    @Override
    public <E extends Event> EventBrokerServiceTestImpl invoke(E event) {
        if (!this.messages.containsKey(event.getClass()))
            return this;
        this.messages.get(event.getClass())
                     .forEach(l -> {
                         try {
                             l.invoke(event);
                         }
                         catch (Exception e) {
                             e.printStackTrace();
                             System.exit(1);
                         }
                     });
        return this;
    }
}
