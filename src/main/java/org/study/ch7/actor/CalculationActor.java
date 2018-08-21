package org.study.ch7.actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class CalculationActor extends UntypedActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public void onReceive(Object message) throws Exception {
        if (message instanceof Integer) {
            Integer msg = (Integer)message;
            log.info("CalculationActor received {}", msg);
            work(msg);
            getSender().tell(msg * 2, getSelf());
        } else {
            unhandled(message);
        }
    }

    private void work(Integer msg) throws InterruptedException {
        log.info("CalculationActor working on " + msg);
        Thread.sleep(1000);
        log.info("CalculationActor completed " + msg);
    }
}
