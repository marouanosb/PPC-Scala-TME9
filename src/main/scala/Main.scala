package upmc.akka.ppc

import akka.actor.{Props, Actor, ActorRef, ActorSystem}

object Concert extends App {
  println("Starting Mozart's dice game")

  val system = ActorSystem("MozartGame")

  val database = system.actorOf(Props[DataBaseActor], "database")
  val player = system.actorOf(Props[PlayerActor], "player")
  val provider = system.actorOf(Props(classOf[ProviderActor], database), "provider")
  val conductor = system.actorOf(Props(classOf[ConductorActor], provider, player), "conductor")

  conductor ! StartGame
}
