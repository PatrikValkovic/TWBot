package cz.valkovic.twbot.modules.core.timing;

import java.time.Duration;
import java.time.Instant;

/**
 * Allows to execute callback at specific time or regularly every timestamp.
 */
public interface TimingService {

    /**
     * Execute callback at specific time.
     * @param callback Callback to execute.
     * @param when When to execute the callback.
     */
    TimingRef executeAt(Runnable callback, Instant when);

    /**
     * Execute callback every duration time.
     * Callback will be executed immediately and then every duration time.
     * @param callback Callback to execute.
     * @param duration How often execute the callback.
     * @return Object that can be later used for removing the callback.
     */
    default TimingRef executeEvery(Runnable callback, Duration duration) {
        return this.executeEveryWithDelay(callback, duration, Instant.now());
    }

    /**
     * Execute callback at firstExecution and then every duration time.
     * @param callback Callback to execute.
     * @param duration How often execute the callback.
     * @param firstExecution When to start with the execution.
     * @return Object that can be later used for removing the callback.
     */
    TimingRef executeEveryWithDelay(Runnable callback, Duration duration, Instant firstExecution);

    /**
     * Execute callback after firstExecution time and then every duration time.
     * @param callback Callback to execute.
     * @param duration How often execute the callback.
     * @param firstExecution How long to wait for the first execution.
     * @return Object that can be later used for removing the callback.
     */
    default TimingRef executeEveryWithDelay(Runnable callback, Duration duration, Duration firstExecution){
        return this.executeEveryWithDelay(callback, duration, Instant.now().plus(firstExecution));
    }

    /**
     * Removes callback from the execution.
     * @param o Object representing the task, previously returned from one of the `execute*` methods.
     */
    void remove(TimingRef o);

    /**
     * Stops the timing and join the thread.
     */
    void stopAndJoin();

}
