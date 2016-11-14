//package app.actors
//
//import java.util.Calendar
//
//import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, Terminated}
//
//import scala.collection.mutable
//import WorkPullingPattern._
//import akka.util.Timeout
//import app.actors.ListingActor.{ParsedListing, ProcessMessages, StartConsuming}
//import app.actors.ParsingActor.ParseListing
//import app.actors.ReportingActor.ReportData
//import com.typesafe.config.ConfigFactory
//
//import scala.collection.IterableLike
//import scala.reflect.ClassTag
//import org.slf4j.LoggerFactory
//import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords}
//
//import scala.concurrent.Future
//import scala.concurrent.duration._
//import akka.pattern.ask
//import app.Reporting
//import app.actors.ListingConsumerActor.StartConsuming
//import app.kafkaclient.{Consumer, KafkaMetaData}
//import app.model.Listing
//
//import scala.collection.JavaConverters._
//
//
//object WorkPullingPattern {
//  sealed trait Message
//  trait Epic[T] extends Iterable[T] //used by master to create work (in a streaming way)
//  case object GimmeWork extends Message
//  case object CurrentlyBusy extends Message
//  case object WorkAvailable extends Message
//  case class RegisterWorker(worker: ActorRef) extends Message
//  case class Work[T](work: T) extends Message
//}
//
//class Master[T] extends Actor {
//  val log = LoggerFactory.getLogger(getClass)
//  val workers = mutable.Set.empty[ActorRef]
//  var currentEpic: Option[Epic[T]] = None
//
//  def receive = {
//    case epic: Epic[T] ⇒
//      if (currentEpic.isDefined)
//        sender ! CurrentlyBusy
//      else if (workers.isEmpty)
//        log.error("Got work but there are no workers registered.")
//      else {
//        currentEpic = Some(epic)
//        workers foreach { _ ! WorkAvailable }
//      }
//
//    case RegisterWorker(worker) ⇒
//      log.info(s"worker $worker registered")
//      context.watch(worker)
//      workers += worker
//
//    case Terminated(worker) ⇒
//      log.info(s"worker $worker died - taking off the set of workers")
//      workers.remove(worker)
//
//    case GimmeWork ⇒ currentEpic match {
//      case None ⇒
//        log.info("workers asked for work but we've no more work to do")
//      case Some(epic) ⇒
//        val iter = epic.iterator
//        if (iter.hasNext)
//          sender ! Work(iter.next)
//        else {
//          log.info(s"done with current epic $epic")
//          currentEpic = None
//        }
//    }
//  }
//}
//
//abstract class Worker[T: ClassTag](val master: ActorRef)(implicit manifest: Manifest[T]) extends Actor {
//  implicit val ec = context.dispatcher
//
//  override def preStart {
//    master ! RegisterWorker(self)
//    master ! GimmeWork
//  }
//
//  def receive = {
//    case WorkAvailable ⇒
//      master ! GimmeWork
//    case Work(work: T) ⇒
//      // haven't found a nice way to get rid of that warning
//      // looks like we can't suppress the erasure warning: http://stackoverflow.com/questions/3506370/is-there-an-equivalent-to-suppresswarnings-in-scala
//      doWork(work) onComplete { case _ ⇒ master ! GimmeWork }
//  }
//
//  def doWork(work: T): Future[_]
//}
//
///*object MyActorSystem extends App with KafkaMetaData
//with  Consumer{
//  type Work = ConsumerRecord[String, String]
//  val system = ActorSystem("monitoring-actors-1", ConfigFactory.load())
//  val masterName = "ListingCoordinator"
//  val master = system.actorOf(Props[Master[Work]], masterName)
//
//  implicit val timeout = Timeout(5.seconds)
//  lazy val parsingActor = system.actorOf(ParsingActor.props)
//  lazy val reportingActor = system.actorOf(ReportingActor.props)
//  val workerActor = system.actorOf(Props(new ReportWorkerActor()))
//
//  consume(kafkaConsumer) match {
//    case Some(records) => master ! new Epic[Work]{ override val iterator = records.asScala.iterator }
//    case _ =>
//  }
//
//  class ReportWorkerActor extends Worker[Work](master) {
//    override def doWork(work: Work): Future[_] = {
//      for {//cascading various actors with ask, only when all are finished yield returns
//        parsedListing <- (parsingActor ? ParseListing(work.value)).mapTo[ParsedListing]
//        _ <- reportingActor ? ReportData(parsedListing.listing)
//      } yield ()
//    }
//  }
//}*/
//
//object ListingConsumerActor {
//  def props = Props[ListingActor]
//  case object StartConsuming//case objects are serializable to/from streams
//  //case class ParsedListing(listing: Option[Listing])
//  case class ProcessMessages(consumerRecords: ConsumerRecords[String, String])
//  //case object FinishedWork
//}
//
//class ListingConsumerActor
//  extends Actor
//    with  KafkaMetaData
//    with  ActorLogging
//    with  Consumer
//    with Reporting {
//
//  import ListingConsumerActor.{StartConsuming, ProcessMessages}
//
//  type Work = ConsumerRecord[String, String]
//
//  val masterName = "ListingCoordinator"
//  val master = context.actorOf(Props[Master[Work]], masterName)
//
//  implicit val timeout = Timeout(5.seconds)
//  lazy val parsingActor = context.actorOf(ParsingActor.props)
//  lazy val reportingActor = context.actorOf(ReportingActor.props)
//  val workerActor = context.actorOf(Props(new ReportWorkerActor()))
//
//  override def receive = processAndWait
//
//  def processAndWait: Receive = {
//    case StartConsuming =>
//      println("::----------::")
//      consume(kafkaConsumer) match {
//        case Some(records) =>
//          master ! new Epic[Work] {
//            override val iterator = records.asScala.iterator
//          }
//          self ! StartConsuming
//        case _ => self ! StartConsuming
//
//      }
//    /*case ProcessMessages(consumerRecords) =>
//      log.info(s"Processing Messages -> listings Count:${consumerRecords.count()}   ${Calendar.getInstance().getTime}")
//      val futures = consumerRecords.asScala.map {
//        cr => parseAndReportListing(cr)
//      }
//      Future.sequence(futures).map(x => self ! StartConsuming) //when all the futures return*/
//  }
//
//  class ReportWorkerActor extends Worker[Work](master) {
//    override def doWork(work: Work): Future[_] = {
//      for {//cascading various actors with ask, only when all are finished yield returns
//        parsedListing <- (parsingActor ? ParseListing(work.value)).mapTo[ParsedListing]
//        _ <- reportingActor ? ReportData(parsedListing.listing)
//      } yield ()
//    }
//  }
//
//}
//
//object Application2
//  extends App
//    with LazyLogging {
//
//  Kamon.start()
//
//  logger.info("START APPLICATION ...")
//  val actorSystem = ActorSystem("monitoring-actors-2", ConfigFactory.load())
//  val healthcheckActor = actorSystem.actorOf(HttpHealthcheckActor.props, HttpHealthcheckActor.name)
//  val mainActor = actorSystem.actorOf(Props(new ListingConsumerActor))
//
//  logger.info("starting main actor")
//  mainActor ! ListingConsumerActor.StartConsuming
//}
//
//
//
//
//
