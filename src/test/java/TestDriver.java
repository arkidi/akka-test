import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.study.ch5.actor.PingActor;
import org.study.ch6.actor.PingActorWithoutRouter;
import org.study.ch7.actor.BlockingActor;
import org.study.ch7.actor.NonblockingActor;

public class TestDriver {
    public static ActorSystem actorSystem;

    @BeforeAll //JUnit 4의 @BeforeClass
    static void setup() {
        actorSystem = ActorSystem.create("TestSystem");
        LoggingAdapter log = Logging.getLogger(actorSystem, actorSystem);
    }

    @Test
    @DisplayName("nothing test")
    public void nothing() {

    }

    @Test
    @DisplayName("STATE MACHINE 테스트")
    public void should_akka_statemachine() throws InterruptedException {
        ActorRef ping = actorSystem.actorOf(Props.create(PingActor.class), "pingActor");
        ping.tell("work", ActorRef.noSender());
        ping.tell("work", ActorRef.noSender());
        ping.tell("reset", ActorRef.noSender());
        Thread.sleep(10000);
    }

    @Test
    @DisplayName("Router 테스트")
    public void should_akka_router() throws InterruptedException {
        ActorRef ping = actorSystem.actorOf(Props.create(org.study.ch6.actor.PingActor.class), "pingActor");
        ping.tell("start", ActorRef.noSender());
        Thread.sleep(10000);
    }

    @Test
    @DisplayName("Router 제외 버전 테스트")
    public void should_akka_without_router() throws InterruptedException {
        ActorRef ping  = actorSystem.actorOf(Props.create(PingActorWithoutRouter.class), "pingActorWithoutRouter");
        ping.tell("start", ActorRef.noSender());
        Thread.sleep(10000);
    }

    @Test
    @DisplayName("Future 블록킹 테스트")
    public void should_akka_Blocking() throws InterruptedException {
        ActorRef blockingActor = actorSystem.actorOf(Props.create(BlockingActor.class), "blockingActor");
        blockingActor.tell(10, ActorRef.noSender());
        blockingActor.tell("hello", ActorRef.noSender());
        Thread.sleep(10000);
    }

    @Test
    @DisplayName("Future 논블록킹 테스트")
    public void should_akka_Nonblocking() throws InterruptedException {
        ActorRef blockingActor = actorSystem.actorOf(Props.create(NonblockingActor.class), "nonBlockingActor");
        blockingActor.tell(10, ActorRef.noSender());
        blockingActor.tell("hello", ActorRef.noSender());
        Thread.sleep(10000);
    }

    @AfterAll
    public static void teardown() {
        actorSystem.shutdown();
    }
}
