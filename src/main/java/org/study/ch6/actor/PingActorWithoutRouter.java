package org.study.ch6.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class PingActorWithoutRouter extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef childRef;

    public PingActorWithoutRouter() {
        childRef = getContext().actorOf(Props.create(Ping1Actor.class), "ping1Actor");
    }

    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
           for (int i = 0; i < 10; i++)
               childRef.tell(i, getSelf());
           log.info("PingActor sent 10 messages to the router");
        } else
            unhandled(message);
    }
}
