package cz.valkovic.java.twbot.services.messaging;

public interface Listener<Event extends Message> {

    default void invoke(Message e){
        this.invokeChecked((Event)e);
    }

    void invokeChecked(Event e);
}
