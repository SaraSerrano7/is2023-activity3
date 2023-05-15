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
        //todo: si al final tengo que hacer que falle, hacer el test como en iz-zio-playground > ExampleSpec
        for 
          randomNumber <- Random.nextInt
          randomString <- Random.nextString(randomNumber)
          _ <- TestConsole.feedLines(randomString, randomNumber.toString())//(randomChar.toString(), "3")
          output <- TestConsole.silent(readNumber)
        yield assertTrue(output == randomNumber.toInt)

      },
      test("generateList"){
        for
          _ <- TestRandom.feedInts(-345, 245765432, 274536, -8357, 0)
          result <- TestConsole.silent(generateList(5))
        yield assertTrue(result == List(-345, 245765432, 274536, -8357, 0))
      },
      test("program"){
        ???
      }
    )
