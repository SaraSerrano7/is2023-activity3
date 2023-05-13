package hello

import zio.*

object Hello extends ZIOAppDefault:

  val run: ZIO[Any, Nothing, Unit] =
    Console.printLine("Hello").orDie
