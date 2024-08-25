package war.in.kursk

import cats.effect.{ExitCode, IO, IOApp, Resource}
import com.suprnation.actor.ActorSystem

import io.github.timwspence.cats.stm.*

import hashgraph.*

object HashgraphApp extends IOApp:
  override def run(args: List[String]): IO[ExitCode] =
    val actorSystemResource: Resource[IO, ActorSystem[IO]] = ActorSystem[IO]("HashgraphSystem")

    actorSystemResource.use: system =>
      for
        alice <- HashgraphActor(system, "alice")
        bob <- HashgraphActor(system, "bob")
        claire <- HashgraphActor(system, "claire")
        doug <- HashgraphActor(system, "doug")
        
        _ <- alice ! HashgraphEvent(bob)
        _ <- bob ! HashgraphEvent(alice)
        _ <- claire ! HashgraphEvent(bob)

        _ <- system.waitForTermination
      yield ExitCode.Success