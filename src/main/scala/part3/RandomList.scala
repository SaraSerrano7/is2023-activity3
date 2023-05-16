package part3

import zio.*

object RandomList extends ZIOAppDefault:

  val readValidNumber: ZIO[Any, Nothing, Int] = 
    Console
      .readLine("Enter number between 0 and 10: ")
      .orDie.flatMap{
        str => 
          ZIO.attempt{
            str.toInt
        }
      }.flatMap{
        num => 
          if num < 0 || num > 10 then readValidNumber else ZIO.succeed(num)
      }
      .catchAll{
        case _: NumberFormatException => readValidNumber
      }

  def generateList(n: Int): ZIO[Any, Nothing, List[Int]] = 
    def myFunc2(n: Int, intAcc: List[Int]): ZIO[Any, Nothing, List[Int]] = 
    if n > 1 then
        for 
            num <- zio.Random.nextInt
            result <- myFunc2(n-1, intAcc) 
        yield( result.::(num))
         
        else    
            for 
                num <- zio.Random.nextInt
            yield (intAcc.::(num))
    
    myFunc2(n, List[Int]())

  val program: ZIO[Any, Nothing, Unit] = 
    for 
      number <- readValidNumber
      list <- generateList(number)
      _ <- Console.printLine(list).orDie
    yield ()

  val run = program
