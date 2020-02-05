package cz.valkovic.java.twbot.modules.core.observable;

import cz.valkovic.java.twbot.modules.core.execution.ExecutionService;
import cz.valkovic.java.twbot.modules.core.logging.LoggingService;
import javax.inject.Inject;

public class ObservableFactoryImpl implements ObservableFactory {

    private final LoggingService log;
    private final ExecutionService execution;

    @Inject
    public ObservableFactoryImpl(LoggingService log, ExecutionService execution) {
        this.log = log;
        this.execution = execution;
    }

    @Override
    public <T> Observable<T> Create(T value) {
        return new Observable<T>(value, this.log, this.execution);
    }
}
