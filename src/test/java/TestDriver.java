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
        ping.tell("reset", ActorRef.noSender());
        Thread.sleep(10000);
    }

    @AfterAll
    public static void teardown() {
        actorSystem.shutdown();
    }
}
