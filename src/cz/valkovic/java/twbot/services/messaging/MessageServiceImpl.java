package cz.valkovic.java.twbot.services.messaging;

import cz.valkovic.java.twbot.services.logging.LoggingService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Singleton
public class MessageServiceImpl implements MessageService {

    private static final int JOIN_TIME = 10000;
    private static final int LOOP_WAIT = 1000;

    //region threading
    @AllArgsConstructor
    @NoArgsConstructor
    private class Invokation {
        @Getter
        @Setter
        Message m;
        @Getter
        @Setter
        Listener l;
    }

    private class MessagingThreadRuntime extends Thread {
        MessagingThreadRuntime(LoggingService log) {
            this.log = log;
            this.setName("Messaging thread");
        }

        private LoggingService log;
        private BlockingQueue<Invokation> invocations = new LinkedBlockingDeque<>();
        private AtomicBoolean working = new AtomicBoolean(true);

        @Override
        public void run() {
            try {
                while (working.get() || !this.invocations.isEmpty()) {
                    Invokation i = this.invocations.poll(LOOP_WAIT, TimeUnit.MILLISECONDS);
                    if (i == null) {
                        continue;
                    }
                    try {
                        this.log.getMessaging().debug(String.format(
                                "Invoking event %s on listener %s",
                                i.m.getClass().getSimpleName(),
                                i.l.getClass().getSimpleName()
                        ));
                        i.l.invoke(i.m);
                        this.log.getMessaging().debug(String.format(
                                "Invoked event %s on listener %s",
                                i.m.getClass().getSimpleName(),
                                i.l.getClass().getSimpleName()
                        ));
                    }
                    catch (Exception e) {
                        String m = String.format(
                                "Failed when invoking listener %s for event %s",
                                i.l.getClass().getSimpleName(),
                                i.m.getClass().getSimpleName()
                        );
                        this.log.getMessaging().warn(m, e);
                    }
                }
            }
            catch (InterruptedException e) {
                this.log.getMessaging().warn("Messaging thread interrupted, ending");
            }
        }
    }

    private MessagingThreadRuntime t;
    //endregion


    private LoggingService log;

    @Inject
    public MessageServiceImpl(LoggingService log) {
        this.log = log;
        this.t = new MessagingThreadRuntime(log);
        this.t.start();
    }


    //region interface
    @Override
    public void waitToAllEvents() throws InterruptedException {
        this.t.working.set(false);
        this.log.getMessaging().debug("Set end of message interpretations");
        try {
            this.t.join(JOIN_TIME);
            this.log.getMessaging().info("Message thread joined");
        }
        catch(InterruptedException e){
            this.log.getMessaging().error("Couldn't join the messaging thread", e);
            throw e;
        }
    }


    private Map<Class<? extends Message>, List<Listener>> callbacks = new HashMap<>();

    @Override
    public synchronized <Event extends Message> MessageServiceImpl listenTo(Class<Event> listenTo, Listener<Event> listener) {
        if (!this.callbacks.containsKey(listenTo)) {
            this.callbacks.put(listenTo, new ArrayList<>());
        }
        callbacks.get(listenTo).add(listener);
        this.log.getMessaging().debug(String.format(
                "New listener %s for event %s",
                listener.getClass().getSimpleName(),
                listenTo.getSimpleName()
        ));
        return this;
    }

    @Override
    public synchronized <Event extends Message> MessageServiceImpl invoke(Event event) {
        this.log.getMessaging().debug("Received event " + event.getClass().getSimpleName());

        if (!this.callbacks.containsKey(event.getClass())) {
            return this;
        }

        List<Listener> callbacks = this.callbacks.get(event.getClass());

        this.log.getMessaging().debug(String.format(
                "Event %s has %d listeners",
                event.getClass().getSimpleName(),
                callbacks.size()
        ));

        callbacks.stream()
                 .map(l -> new Invokation(event, l))
                 .forEach(i -> {
                     try {
                         this.t.invocations.put(i);
                     }
                     catch (InterruptedException e) {
                         String m = String.format(
                                 "Interrupted of adding listener %s for event %s",
                                 i.l.getClass().getName(),
                                 event.getClass().getName()
                         );
                         this.log.getMessaging().warn(m, e);
                     }
                 });
        return this;
    }
    //endregion
}
