package cz.valkovic.twbot.modules.core.events;

/**
 * Interface to register or invoke an event.
 */
public interface EventBrokerService {

    /**
     * Register listener for specific event.
     * @param listenTo Type of event listen to.
     * @param listener Listener to call every time event `listenTo` occured.
     * @param <E> Type of the event.
     * @return Key to allow remove the listener later.
     */
    <E extends Event> Object listenTo(Class<E> listenTo, Listener<E> listener);

    /**
     * Remove listener from the event.
     * @param o Value returned from the `listenTo` method.
     * @return Instance self.
     */
    EventBrokerService remove(Object o);

    /**
     * Invoke event.
     * @param e Event to invoke.
     * @param <E> Type of event that is invoking.
     * @return Instance self.
     */
    <E extends Event> EventBrokerService invoke(E e);
}
