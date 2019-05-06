package cz.valkovic.java.twbot.services.messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageServiceTestImpl implements MessageService {

    private Map<Class<? extends Message>, List<Listener<? extends Message>>> messages = new HashMap<>();

    @Override
    public <Event extends Message> MessageServiceTestImpl listenTo(Class<Event> listenTo, Listener<Event> listener) {
        if (!this.messages.containsKey(listenTo))
            this.messages.put(listenTo, new ArrayList<>());
        this.messages.get(listenTo).add(listener);
        return this;
    }

    @Override
    public <Event extends Message> MessageServiceTestImpl invoke(Event event) {
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

    @Override
    public void waitToAllEvents() {
    }
}
