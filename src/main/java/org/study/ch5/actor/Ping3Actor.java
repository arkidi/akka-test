package org.study.ch5.actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Ping3Actor extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            String msg = (String)message;
            log.info("Ping3 received {}", msg);
            work();
            getSender().tell("done", getSelf());
        }
    }

    private void work() throws InterruptedException {
        Thread.sleep(1000);
        log.info("Ping3 working...");
    }
}
