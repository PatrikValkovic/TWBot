package cz.valkovic.java.twbot.services.messaging;

public interface Listener<Event extends Message> {

    @SuppressWarnings("unchecked")
    default void invoke(Message e) throws Exception {
        this.invokeChecked((Event)e);
    }

    void invokeChecked(Event e) throws Exception;
}
