package cz.valkovic.twbot.modules.core.events;

/**
 * Represent interface that respond to events.
 * @param <E> Event type respond to.
 */
public interface Listener<E extends Event> {

    /**
     * Invoke generic `Event` on the listener.
     * @param e Event that should the object respond to.
     * @throws ClassCastException When the passed event is not the required type.
     * @throws Exception Any error from the implementation.
     */
    @SuppressWarnings("unchecked")
    default void invoke(Event e) throws ClassCastException, Exception {
        this.invokeSafe((E)e);
    }

    /**
     * Invoke event on the listener.
     * @param e Event that should the object respond to.
     * @throws Exception Any error from the implementation.
     */
    void invokeSafe(E e) throws Exception;
}
