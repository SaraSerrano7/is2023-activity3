package part2

import zio.*

object RandomList extends ZIOAppDefault:

  val readNumber: ZIO[Any, Nothing, Int] = ???

  def generateList(n: Int): ZIO[Any, Nothing, List[Int]] = ???

  val program: ZIO[Any, Nothing, Unit] = ???

  val run = program
