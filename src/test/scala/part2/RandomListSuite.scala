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


        // for
        //   _ <- TestRandom.feedInts(1, 2)
        //   r1 <- Random.nextInt
        //   r2 <- Random.nextInt
        // // yield assertZIO(Random.nextInt)(equalTo(1))   
        // yield assertTrue(List(r1, r2) == List(1, 2))


        // val myList = List[Int]()
        // val myList2 = (1 to 5).toList
        // val expected =
        //   for 
        //     _ <- TestRandom.feedInts(3, 53, -456, 0, -1)
        //     num <- zio.Random.nextInt
        //   // yield (List.apply(num))
        //   yield (myList.::(num))

        // assertZIO(expected)(equalTo(List(3, 53, -456, 0, -1)))


//        for 
          /*
          1. TestRandom.feedInts(3, 53, -456, 0, -1)
          2. TestConsole.feedLines(5)
          3. 
          for 
            _ <- TestRandom.feedInts(1, 2)
            num <- zio.Random.nextInt
          yield (List.apply(num))
          */
//          _ <- TestRandom.feedInts(3, 53, -456, 0, -1)
          // _ <- TestConsole.feedLines(5)
//          output <- TestConsole.silent(generateList(5))
          // randomLength <- Random.nextInt
          // randomList <- (1 to randomLength).toList.map(Random.nextInt)
          //  <- ???
        // yield assertTrue(output == List(3, 53, -456, 0, -1))
        
      },
      test("program"){
        ???
      }
    )
