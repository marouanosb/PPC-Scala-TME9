package upmc.akka.ppc

import akka.actor.{Props, Actor, ActorRef, ActorSystem}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global
import scala.util.Random

case object StartGame

class ConductorActor(provider: ActorRef, player: ActorRef) extends Actor {

  import DataBaseActor._

  val random = new Random()

  // Lancer deux dés : somme entre 2 et 12
  def rollDice(): Int = {
    val d1 = random.nextInt(6) + 1
    val d2 = random.nextInt(6) + 1
    d1 + d2
  }

  def receive = {
    case StartGame => {
      val diceSum = rollDice()
      provider ! GetMeasure(diceSum)
    }
    case m: Measure => {
      player ! m
      // Attendre 1800 ms puis relancer le jeu
      context.system.scheduler.scheduleOnce(1800.milliseconds, self, StartGame)
    }
  }
}
