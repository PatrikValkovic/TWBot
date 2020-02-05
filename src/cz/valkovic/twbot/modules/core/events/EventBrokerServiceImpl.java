package cz.valkovic.twbot.modules.core.events;

import cz.valkovic.twbot.modules.core.execution.ExecutionService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Singleton
public class EventBrokerServiceImpl implements EventBrokerService {

    private LoggingService log;
    private ExecutionService execution;

    @SuppressWarnings("rawtypes")
    private Map<Class<? extends Event>, List<Listener>> callbacks = new HashMap<>();

    @Inject
    public EventBrokerServiceImpl(LoggingService log,
                                  ExecutionService execution) {
        this.log = log;
        this.execution = execution;
    }


    @Override
    public void waitToAllEvents() throws InterruptedException {
        this.execution.waitAndExit();
    }


    @AllArgsConstructor
    private static class ListenerRemoveObject<E extends Event> {
        @Getter
        Class<E> key;
        @Getter
        Listener<E> instance;
    }

    @Override
    public synchronized <e extends Event> Object listenTo(Class<e> listenTo, Listener<e> listener) {
        if (!this.callbacks.containsKey(listenTo)) {
            this.callbacks.put(listenTo, new ArrayList<>());
        }
        callbacks.get(listenTo).add(listener);
        this.log.getEvents().debug(String.format(
                "New listener %s for event %s",
                listener.getClass().getSimpleName(),
                listenTo.getSimpleName()
        ));
        return new ListenerRemoveObject<>(listenTo, listener);
    }

    @Override
    public synchronized <E extends Event> EventBrokerServiceImpl invoke(E event) {
        this.log.getEvents().debug("Received event " + event.getClass().getSimpleName());

        if (!this.callbacks.containsKey(event.getClass())) {
            return this;
        }

        @SuppressWarnings("rawtypes")
        List<Listener> callbacks = this.callbacks.get(event.getClass());

        this.log.getEvents().debug(String.format(
                "Event %s has %d listeners",
                event.getClass().getSimpleName(),
                callbacks.size()
        ));

        callbacks.forEach(callback -> {
            this.execution.run(() -> {
                try {
                    callback.invoke(event);
                }
                catch(Exception e) {
                    this.log.getEvents().warn(String.format(
                            "Error in handler %s for event %s",
                            callback.getClass().getCanonicalName(),
                            event.getClass().getSimpleName()
                    ));
                }
            });
        });

        return this;
    }

    @Override
    public EventBrokerService remove(Object o) {
        @SuppressWarnings("rawtypes")
        ListenerRemoveObject stored = (ListenerRemoveObject)o;
        if (!this.callbacks.containsKey(stored.key)) {
            return this;
        }
        boolean removed = callbacks.get(stored.key).remove(stored.instance);
        if(removed)
            this.log.getEvents().debug(String.format(
                    "Listener %s removed from event %s",
                    stored.instance.getClass().getSimpleName(),
                    stored.key.getSimpleName()
            ));
        else
            this.log.getEvents().debug(String.format(
                    "Listener %s couldn't be removed from event %s",
                    stored.instance.getClass().getSimpleName(),
                    stored.key.getSimpleName()
            ));
        return this;
    }
}
