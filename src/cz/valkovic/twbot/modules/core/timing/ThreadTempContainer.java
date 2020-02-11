package cz.valkovic.twbot.modules.core.timing;

import cz.valkovic.twbot.modules.core.execution.ExecutionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.function.Function;

@AllArgsConstructor
public class ThreadTempContainer implements TimingRef, Comparable<ThreadTempContainer> {

    @Getter
    @Setter
    Instant whenToExecute;

    @Getter
    @Setter
    boolean inQueue;

    @Getter
    Function<ExecutionService, Instant> callback;

    @Override
    public int compareTo(ThreadTempContainer o) {
        Instant selfTime;
        Instant otherTime;
        synchronized (this){
            selfTime = this.whenToExecute;
        }
        synchronized (o){
            otherTime = o.whenToExecute;
        }
        return selfTime.compareTo(otherTime);
    }
}
