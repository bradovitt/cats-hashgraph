package war.in.kursk.hashgraph

import cats.effect.IO

import com.suprnation.actor.Actor.{Actor, Receive}
import com.suprnation.actor.ActorRef.ActorRef
import com.suprnation.actor.ActorSystem

import io.github.timwspence.cats.stm.STM

final class HashgraphActor private (stm: STM[IO])(
  contacts: stm.TVar[List[ActorRef[IO, HashgraphEvent]]],
) extends Actor[IO, HashgraphEvent]:

  import stm.*

  override def receive: Receive[IO, HashgraphEvent] = event => 
    for 
      _ <- commit:
        randomContacts *> contacts.modify(_ :+ event.sender)
      _ <- IO(println(event))
    yield ()

  private val randomContacts = contacts.get.map: 
    util.Random.shuffle(_).take(2)

  // private val logic = for
  //   transaction <- transactions.read
  // yield ()
        
object HashgraphActor:

  def apply(system: ActorSystem[IO], id: String): IO[ActorRef[IO, HashgraphEvent]] = 

    for
      actor <- STM.runtime[IO].flatMap: stm => 
        import stm.*

        for
          contacts <- commit(
            TVar.of(
              List[ActorRef[IO, HashgraphEvent]]()
            )
          )
        yield new HashgraphActor(stm)(contacts)
      ref <- system.actorOf(actor, id)
    yield ref