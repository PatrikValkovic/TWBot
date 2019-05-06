package cz.valkovic.java.twbot.services.messaging;

public interface MessageService {

    <Event extends Message> MessageService listenTo(Class<Event> listenTo, Listener<Event> listener);

    <Event extends Message> MessageService invoke(Event event);

    void waitToAllEvents() throws InterruptedException;
}
