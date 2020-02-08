package cz.valkovic.twbot.modules.core.observable;

import cz.valkovic.twbot.modules.core.execution.ExecutionService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
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
    private List<WeakReference<Consumer<T>>> consumers = new ArrayList<>();
    @AllArgsConstructor
    private class ObservableLock {
        @Getter
        WeakReference<Consumer<T>> ref;
        @Getter
        Consumer<T> val;
    }

    /**
     * Get value of the observable item.
     * @return Current value without observation.
     */
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
        for (WeakReference<Consumer<T>> c : new ArrayList<>(this.consumers)) {
            if(c.get() == null) {
                this.log.getObservable().debug("Removing observable because its null");
                this.consumers.remove(c);
            }
            else
                this.callCallback(c.get());
        }
    }

    /**
     * Add observer, that get notified about the changes.
     * The callback is called immediately (not after the first change) in the same thread.
     * Callbacks during lifetime are called in execution thread.
     * @param callback Callback to call when value changes.
     * @return Return reference to Object, that can be later use to remove the observer.
     */
    public synchronized Object observe(Consumer<T> callback) {
        var ref = new WeakReference<>(callback);
        this.consumers.add(ref);
        callback.accept(this.getValue());
        return new ObservableLock(ref, callback);
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
        @SuppressWarnings("unchecked")
        boolean removed = this.consumers.remove(((ObservableLock)o).ref);
        if (!removed)
            this.log.getObservable().warn("Couldn't remove callback from observer");
        return removed;
    }

    private void callCallback(Consumer<T> callback) {
        try {
            this.log.getObservable().debug(String.format(
                    "Adding observer to execution because of change: %s.",
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
