package part2

import zio.*
import zio.test.*

import zio.Exit.*
import RandomList.*
import zio.Random
import zio.test.TestRandom
import zio.test.Assertion.*
import zio.Cause.Empty
import zio.Cause.Fail
import zio.Cause.Die
import zio.Cause.Interrupt
import zio.Cause.Stackless
import zio.Cause.Then
import zio.Cause.Both

object RandomListSuite extends ZIOSpecDefault:

  val spec =
    suite("part2")(
      test("readNumber: reading number"){
        for
          random <- Random.nextInt
          _ <- TestConsole.feedLines(random.toString())
          output <- TestConsole.silent(readNumber)
        yield assertTrue(output == random.toInt)

      },
      test("readNumber: not reading number"){
        for 
          randomNumber <- Random.nextInt
          randomString <- Random.nextString(randomNumber)
          _ <- TestConsole.feedLines(randomString)
          //en el informe comentar que puse readNumber dentro de un attempt, y que por eso me daba success
          //esto si que muere, pero en el canal de error tenemos un nothing.
          //para mas info, con CatchAllCause podemos ver que hay. Haya lo que haya, a
          exit <- readNumber.catchAllCause(_ => ZIO.fail(())).exit
        yield assertTrue(exit == Exit.fail(()))
      },
      test("generateList"){
        for
          _ <- TestRandom.feedInts(-345, 245765432, 274536, -8357, 0)
          result <- TestConsole.silent(generateList(5))
        yield assertTrue(result == List(-345, 245765432, 274536, -8357, 0))
      },
      test("program"){
        for 
          _ <- TestConsole.feedLines("5")
          _ <- TestRandom.feedInts(-345, 245765432, 274536, -8357, 0)
          _ <- TestConsole.silent(program)
          output <- TestConsole.output
          _ <- TestConsole.clearOutput
        yield assertTrue(output.last.trim() == List(-345, 245765432, 274536, -8357, 0).toString())
        }
    )
