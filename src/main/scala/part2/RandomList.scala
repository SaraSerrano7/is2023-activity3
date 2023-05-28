package part2

import zio.*
// import scala.util.Random
import zio.Random

object RandomList extends ZIOAppDefault:

  val readNumber: ZIO[Any, Nothing, Int] = 
    Console
    .readLine("Enter number: ")
    .orDie
    .flatMap{
      str => ZIO.attempt(str.toInt)
    }
    .orDie

  def generateList(n: Int): ZIO[Any, Nothing, List[Int]] = 
    def myFunc2(n: Int, intAcc: List[Int]): ZIO[Any, Nothing, List[Int]] = 
    if n > 1 then
        for 
            num <- zio.Random.nextInt
            result <- myFunc2(n-1, intAcc) 
        yield num :: result
         
        else if n == 1 then    
            for 
                num <- zio.Random.nextInt
            yield num :: intAcc

        else
          ZIO.succeed(intAcc)
    
    myFunc2(n, List[Int]())


  val program: ZIO[Any, Nothing, Unit] = 
    for 
      number <- readNumber
      list <- generateList(number)
      _ <- Console.printLine(list).orDie
    yield ()


  val run = program