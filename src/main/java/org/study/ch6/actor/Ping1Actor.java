package org.study.ch6.actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Ping1Actor extends UntypedActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public void onReceive(Object message) throws Exception {
        if(message instanceof Integer) {
            Integer msg = (Integer)message;
            log.info("Ping1Actor({}) received {}", hashCode(), msg);
            work(msg);
        }
    }

    private void work(Integer msg) throws InterruptedException {
        log.info("Ping1Actor({}) working on {}", hashCode(), msg);
        Thread.sleep(1000);
        log.info("Ping1Actor({}) completed", hashCode());
    }
}
