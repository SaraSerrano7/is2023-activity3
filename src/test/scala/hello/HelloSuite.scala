package hello

import zio.*
import zio.test.*

object HelloSuite extends ZIOSpecDefault:

  val spec = test("prints hello") {
    for
      _ <- TestConsole.silent(Hello.run)
      output <- TestConsole.output
    yield assertTrue(output(0) == "Hello\n")
  }
