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

@Singleton
public class MessageServiceImpl implements MessageService {

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

    private static BlockingQueue<Invokation> invokations = new LinkedBlockingDeque<>();

    private static void invokeThread() {
        try {
            while (true) {
                Invokation i = invokations.take();
                try {
                    i.l.invoke(i.m);
                }
                catch (Exception e) {
                    String m = String.format(
                        "Failed when invoking listener %s for event %s",
                            i.l.getClass().getName(),
                            i.m.getClass().getName()
                    );
                    log.getMessaging().warn(m, e);
                }
            }
        }
        catch(InterruptedException e){
            log.getMessaging().debug("Messaging thread interrupted, ending");
        }
    }

    private static LoggingService log;

    @Inject
    public MessageServiceImpl(LoggingService l)
    {
        log = l;
        Thread t = new Thread(MessageServiceImpl::invokeThread);
        t.setDaemon(true);
        t.start();
    }

    //region interface
    private Map<Class<? extends Message>, List<Listener>> callbacks = new HashMap<>();

    @Override
    public synchronized <Event extends Message> void listenTo(Class<Event> listenTo, Listener<Event> listener) {
        if(!this.callbacks.containsKey(listenTo)){
            this.callbacks.put(listenTo, new ArrayList<>());
        }
        callbacks.get(listenTo).add(listener);
        log.getMessaging().debug("New listener for event " + listenTo.getName());
    }

    @Override
    public synchronized <Event extends Message> void invoke(Event event) {
        log.getMessaging().debug("Invoking event " + event.getClass().getName());
        if(!this.callbacks.containsKey(event.getClass())){
            return;
        }
        List<Listener> callbacks = this.callbacks.get(event.getClass());
        log.getMessaging().debug("Event " + event.getClass().getName() + " has " + callbacks.size() + " listeners");
        callbacks.stream()
                 .map(l -> new Invokation(event, l))
                 .forEach(i -> {
                     try {
                         invokations.offer(i, 5000, TimeUnit.MILLISECONDS);
                     }
                     catch (InterruptedException e) {
                         String m = String.format(
                                 "Interrupted of adding listener %s for event %s",
                                 i.l.getClass().getName(),
                                 event.getClass().getName()
                         );
                         log.getMessaging().warn(m, e);
                     }
                 });
    }
    //endregion
}
