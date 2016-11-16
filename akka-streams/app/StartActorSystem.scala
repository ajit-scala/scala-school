import javax.inject.Singleton

import akka.actor.ActorSystem
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class  StartActorSystem {
  val system = ActorSystem("myActors")
  system.scheduler.scheduleOnce(5.seconds) {
    (0 to 10).foreach(println(_))
  }
  system.scheduler.schedule(10.seconds, 5.seconds) {
    (10 to 20).foreach(println(_))
  }
}
