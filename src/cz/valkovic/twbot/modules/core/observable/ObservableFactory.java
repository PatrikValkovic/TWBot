package cz.valkovic.twbot.modules.core.observable;

/**
 * Creates observable objects.
 */
public interface ObservableFactory {

    /**
     * Create observable object with default value.
     * @param value Default value.
     * @param <T> Type of the observable.
     * @return Observable object.
     */
    <T> Observable<T> Create(T value);
}
