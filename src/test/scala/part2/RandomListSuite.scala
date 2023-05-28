package part2

import zio.*
import zio.test.*

import zio.Exit.*
import RandomList.*
import zio.Random
import zio.test.TestRandom
import zio.test.Assertion.*

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
          exit <- readNumber.catchAllCause(_ => ZIO.fail(())).exit
        yield assertTrue(exit == Exit.fail(()))
      },
      test("generateList: empty list"){
        for
          _ <- TestRandom.feedInts(-345, 245765432, 274536, -8357, 0)
          result <- TestConsole.silent(generateList(0))
        yield assertTrue(result == List())
      },
      test("generateList: long 1 list"){
        for
          _ <- TestRandom.feedInts(-345, 245765432, 274536, -8357, 0)
          result <- TestConsole.silent(generateList(1))
        yield assertTrue(result == List(-345))
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
