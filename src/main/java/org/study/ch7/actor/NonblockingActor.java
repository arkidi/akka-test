package org.study.ch7.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.dispatch.OnComplete;
import akka.dispatch.OnFailure;
import akka.dispatch.OnSuccess;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class NonblockingActor extends UntypedActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef child;
    private Timeout timeout = new Timeout(Duration.create(5, "seconds"));
    private final ExecutionContext ec;

    public NonblockingActor() {
        child = context().actorOf(Props.create(CalculationActor.class), "calculationActor");
        ec = context().system().dispatcher();
    }

    public void onReceive(Object message) throws Exception {
        if (message instanceof Integer) {
            Future<Object> future = Patterns.ask(child, message, timeout);

            // onSuccess, onComplete, onFailure 등은 blocking 동작이 아니다.
            future.onSuccess(new SaySuccess<Object>(), ec);
            future.onComplete(new SayComplete<Object>(), ec);
            future.onFailure(new SayFailure<Object>(), ec);
        } else if (message instanceof String) {
            log.info("NonblockingActor received a message: " + message);
        }
    }

    public final class SaySuccess<T> extends OnSuccess<T> {
        public final void onSuccess(T result) throws Throwable {
            log.info("Succeeded with " + result);
        }
    }

    public final class SayComplete<T> extends OnComplete<T> {
        public final void onComplete(Throwable throwable, T result) throws Throwable {
            log.info("Completed.");
        }
    }

    public final class SayFailure<T> extends OnFailure {
        public final void onFailure(Throwable t) throws Throwable {
            log.info("Failed with " + t);
        }
    }

}
