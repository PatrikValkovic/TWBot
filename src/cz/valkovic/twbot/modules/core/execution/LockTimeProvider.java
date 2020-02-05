package cz.valkovic.twbot.modules.core.execution;

/**
 * Provider of lock time for ExecutionService.
 * Only for internal use (to break circular reference].
 * Do not use it in the application.
 */
public interface LockTimeProvider {

    /**
     * Return maximum lock time.
     * @return Lock time.
     */
    int getLockTime();

}
