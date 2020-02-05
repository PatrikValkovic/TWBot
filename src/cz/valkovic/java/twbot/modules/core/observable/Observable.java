package cz.valkovic.java.twbot.modules.core.observable;

import cz.valkovic.java.twbot.modules.core.execution.ExecutionService;
import cz.valkovic.java.twbot.modules.core.logging.LoggingService;
import java.util.LinkedList;
import java.util.function.Consumer;

/**
 * Observable wrapper, that notify all observables, when the value change.
 * So far the callbacks are run on the same thread that changed the variable.
 *
 * @param <T> Type of variable to store.
 */
public class Observable<T> {

    private final ExecutionService execution;
    private final LoggingService log;

    public Observable(T value, LoggingService log, ExecutionService execution) {
        this.log = log;
        this.execution = execution;
        this.setValue(value);
    }


    private T value;
    private LinkedList<Consumer<T>> consumers = new LinkedList<>();

    public synchronized T getValue() {
        return this.value;
    }

    /**
     * Set value and and notify all observers about the change.
     *
     * @param value New value.
     */
    public synchronized void setValue(T value) {
        this.value = value;
        for (Consumer<T> c : this.consumers)
            this.callCallback(c);
    }

    /**
     * Add observer, that get notified about the changes.
     * The callback is called immediately (not after the first change).
     *
     * @param callback Callback to call when value changes.
     * @return Return reference to Object, that can be later use to remove the observer.
     */
    public synchronized Object observe(Consumer<T> callback) {
        this.consumers.add(callback);
        this.callCallback(callback);
        return callback;
    }

    /**
     * Add observer, that get notified about the changes.
     * The callback is called in the rendering thread.
     * The callback is called immediately (not after the first change).
     *
     * @param callback Callback to call when value changes.
     * @return Return reference to Object, that can be later use to remove the observer.
     */
    public synchronized Object observeInRender(Consumer<T> callback) {
        return this.observe(value -> {
            this.execution.runInRender(() -> callback.accept(this.getValue()));
        });
    }

    /**
     * Remove callback from the observers.
     *
     * @param o Object returned from one of the `Observe*` methods.
     * @return True if the object was removed, false otherwise.
     */
    public synchronized boolean unregister(Object o) {
        boolean removed = this.consumers.remove(o);
        if (!removed)
            this.log.getObservable().warn("Couldn't remove callback from observer");
        return removed;
    }

    private void callCallback(Consumer<T> callback) {
        try {
            this.log.getObservable().debug(String.format(
                    "Adding observer %s because of change.",
                    callback.getClass().getSimpleName()
            ));
            this.execution.run(() -> callback.accept(this.getValue()));
        }
        catch (Exception e) {
            this.log.getObservable().warn(String.format(
                    "Exception in observable %s when changed.",
                    callback.getClass().getSimpleName()
            ), e);
        }
    }

}
