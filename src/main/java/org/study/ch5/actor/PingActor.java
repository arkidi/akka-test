package org.study.ch5.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActorWithStash;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

public class PingActor extends UntypedActorWithStash {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef child;

    public PingActor() {
        child = context().actorOf(Props.create(Ping1Actor.class), "Ping1Actor");
        getContext().become(initial);
    }

    public void onReceive(Object o) throws Exception {

    }

    Procedure<Object> initial = new Procedure<Object>() {
        public void apply(Object message) throws Exception {
            if ("work".equals(message)) {
                child.tell("work", getSelf());
                getContext().become(zeroDone);
            } else
                stash();
        }
    };

    Procedure<Object> zeroDone = new Procedure<Object>() {
        public void apply(Object message) throws Exception {
            if ("done".equals(message)) {
                log.info("Received the first done");
                getContext().become(oneDone);
            } else
                stash();
        }
    };

    Procedure<Object> oneDone = new Procedure<Object>() {
        public void apply(Object message) throws Exception {
            if ("done".equals(message)) {
                log.info("Received the second done");
                unstashAll();
                getContext().become(allDone);
            } else
                stash();
        }
    };

    Procedure<Object> allDone = new Procedure<Object>() {
        public void apply(Object message) throws Exception {
            if ("reset".equals(message)) {
                log.info("Received a reset");
                getContext().become(initial);
            }
        }
    };
}
