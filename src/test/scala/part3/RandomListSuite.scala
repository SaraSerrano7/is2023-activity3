package part3

import zio.*
import zio.test.*

import RandomList.*
import part3.RandomList.readValidNumber

object RandomListSuite extends ZIOSpecDefault:

  val spec =
    suite("part3")(
      test("readValidNumber: reading 0 to 10 number"){
        for
          random <- Random.nextIntBetween(0, 10)
          _ <- TestConsole.feedLines(random.toString())
          output <- TestConsole.silent(readValidNumber)
        yield assertTrue(output == random.toInt)

      },
      test("readValidNumber: reading out of scope number"){
        for
          randomBad <- Random.nextInt
          randomOk <- Random.nextIntBounded(10)
          _ <- TestConsole.feedLines(randomBad.toString(), randomOk.toString())
          output <- TestConsole.silent(readValidNumber)
        yield assertTrue(output == randomOk.toInt)

      },
      test("readValidNumber: not reading number"){  
        for 
          randomNumber <- Random.nextInt
          randomString <- Random.nextString(randomNumber)
          randomOk <- Random.nextIntBounded(10)
          _ <- TestConsole.feedLines(randomString, randomNumber.toString(), randomOk.toString())
          output <- TestConsole.silent(readValidNumber)
        yield assertTrue(output == randomOk)
      },
      test("generateList"){
        for
          _ <- TestRandom.feedInts(-345, 245765432, 274536, -8357, 0)
          result <- TestConsole.silent(generateList(5))
        yield assertTrue(result == List(-345, 245765432, 274536, -8357, 0))
      },
      test("program"){
        for 
          randomNumber <- Random.nextInt
          randomString <- Random.nextString(randomNumber)
          _ <- TestConsole.feedLines(randomString, randomNumber.toString(), "5")
          _ <- TestRandom.feedInts(-345, 245765432, 274536, -8357, 0)
          _ <- TestConsole.silent(program)
          output <- TestConsole.output
          _ <- TestConsole.clearOutput
        yield assertTrue(output.last.trim() == List(-345, 245765432, 274536, -8357, 0).toString())
        }
    )
