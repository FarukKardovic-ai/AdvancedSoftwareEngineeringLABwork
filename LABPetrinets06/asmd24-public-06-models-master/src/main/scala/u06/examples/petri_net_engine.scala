// src/main/scala/petrinet/Main.scala

import scala.collection.mutable
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

case class Place(name: String)

case class Transition(
  name: String,
  input: Map[Place, Int],
  output: Map[Place, Int]
)

case class Marking(tokens: Map[Place, Int]) {
  def enabled(t: Transition): Boolean =
    t.input.forall { case (p, n) => tokens.getOrElse(p, 0) >= n }

  def fire(t: Transition): Marking = {
    require(enabled(t), s"Transition ${t.name} not enabled")
    val consumed = t.input.foldLeft(tokens)((acc, kv) =>
      acc.updated(kv._1, acc(kv._1) - kv._2)
    )
    val produced = t.output.foldLeft(consumed)((acc, kv) =>
      acc.updated(kv._1, acc.getOrElse(kv._1, 0) + kv._2)
    )
    Marking(produced)
  }
}

class PetriNetEngine(initialMarking: Marking) {
  private var marking = initialMarking
  private val lock = new Object
  private val waitQueues = mutable.Map.empty[String, mutable.Queue[Promise[Unit]]]

  def requestFire(t: Transition): Future[Unit] = {
    val promise = Promise[Unit]()

    lock.synchronized {
      if (marking.enabled(t)) {
        marking = marking.fire(t)
        println(s"[Fired] ${t.name} -> New marking: ${marking.tokens}")
        promise.success(())
      } else {
        println(s"[Blocked] ${t.name}")
        val queue = waitQueues.getOrElseUpdate(t.name, mutable.Queue())
        queue.enqueue(promise)
      }
    }

    promise.future
  }

  def release(): Unit = lock.synchronized {
    for ((tName, queue) <- waitQueues if queue.nonEmpty) {
      val transitionOpt = transitionSet.find(_.name == tName)
      transitionOpt match {
        case Some(t) if marking.enabled(t) =>
          val promise = queue.dequeue()
          marking = marking.fire(t)
          println(s"[Auto-fired] $tName -> New marking: ${marking.tokens}")
          promise.success(())
        case _ => // do nothing
      }
    }
  }

  var transitionSet: Set[Transition] = Set()

  def setTransitions(ts: Set[Transition]): Unit = lock.synchronized {
    transitionSet = ts
  }
}

object Main extends App {
  val Reader = Place("Reader")
  val Writer = Place("Writer")
  val Mutex = Place("Mutex")

  val readStart = Transition(
    "readStart",
    input = Map(Mutex -> 1),
    output = Map(Mutex -> 1, Reader -> 1)
  )

  val readEnd = Transition(
    "readEnd",
    input = Map(Reader -> 1),
    output = Map()
  )

  val writeStart = Transition(
    "writeStart",
    input = Map(Mutex -> 1),
    output = Map(Writer -> 1)
  )

  val writeEnd = Transition(
    "writeEnd",
    input = Map(Writer -> 1),
    output = Map(Mutex -> 1)
  )

  val marking = Marking(Map(Mutex -> 1))
  val engine = new PetriNetEngine(marking)
  val transitions = Set(readStart, readEnd, writeStart, writeEnd)
  engine.setTransitions(transitions)

  def reader(id: Int): Unit = Future {
    Await.result(engine.requestFire(readStart), 5.seconds)
    println(s"Reader $id is READING...")
    Thread.sleep(500)
    Await.result(engine.requestFire(readEnd), 5.seconds)
    println(s"Reader $id done reading.")
    engine.release()
  }

  def writer(id: Int): Unit = Future {
    Await.result(engine.requestFire(writeStart), 5.seconds)
    println(s"Writer $id is WRITING...")
    Thread.sleep(1000)
    Await.result(engine.requestFire(writeEnd), 5.seconds)
    println(s"Writer $id done writing.")
    engine.release()
  }

  (1 to 3).foreach(reader)
  Thread.sleep(300)
  (1 to 2).foreach(writer)
  Thread.sleep(5000)
}
