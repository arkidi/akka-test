package org.study.ch1;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.study.ch1.actor.PingActor;

/**
 * 핑퐁액터 데모를 위한 메인 클래스
 * @author Seungyoung Yang
 * */
public class Main {

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("TestSystem");
        ActorRef ping = actorSystem.actorOf(Props.create(PingActor.class), "pingActor");
        ping.tell("start", ActorRef.noSender());
    }
}
