package cz.valkovic.java.twbot.services.messaging;

public interface MessageService {

    <Event extends Message> void listenTo(Class<Event> listenTo, Listener<Event> listener);

    <Event extends Message> void invoke(Event event);
}
