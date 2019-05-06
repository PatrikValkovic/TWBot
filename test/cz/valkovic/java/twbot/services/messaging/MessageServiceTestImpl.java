package cz.valkovic.java.twbot.services.messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageServiceTestImpl implements MessageService {

    private Map<Class<? extends Message>, List<Listener<? extends Message>>> messages = new HashMap<>();

    @Override
    public <Event extends Message> void listenTo(Class<Event> listenTo, Listener<Event> listener) {
        if (!this.messages.containsKey(listenTo))
            this.messages.put(listenTo, new ArrayList<>());
        this.messages.get(listenTo).add(listener);
    }

    @Override
    public <Event extends Message> void invoke(Event event) {
        if (!this.messages.containsKey(event.getClass()))
            return;
        this.messages.get(event.getClass())
                     .forEach(l -> l.invoke(event));
    }
}
