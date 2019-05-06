package cz.valkovic.java.twbot.services.messaging;

public interface Listener<Event extends Message> {

    void invoke(Message e);

}
