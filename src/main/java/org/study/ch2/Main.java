package org.study.ch2;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.study.ch2.actor.PingActor;

/**
 * Akka의 계층구조를 보여주기 위한 메인 클래스
 * @author Seungyoung Yang
 **/
public class Main {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("TestSystem");
        ActorRef ping = actorSystem.actorOf(Props.create(PingActor.class), "pingActor");
        ping.tell("work", ActorRef.noSender());
    }
}
