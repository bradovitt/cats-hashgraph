package war.in.kursk.hashgraph

import java.time.Instant as Timestamp

type Signature = Array[Byte]
type Transaction = Int

// final case class HashgraphEvent(
// 	signature: Signature,
// 	parentEvents: (HashgraphEvent, HashgraphEvent),
// 	transactions: Array[Transaction],
// 	timestamp: Timestamp
// )

import com.suprnation.actor.ActorRef.ActorRef
import cats.effect.IO

final case class HashgraphEvent(sender: ActorRef[IO, HashgraphEvent])