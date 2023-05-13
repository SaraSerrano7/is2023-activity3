package part2

import zio.*
import scala.util.Random

object RandomList extends ZIOAppDefault:

  val readNumber: ZIO[Any, Nothing, Int] = 
    Console.readLine("Enter number: ").orDie.flatMap(str => ZIO.attempt(str.toInt)).catchAll{
      case _: NumberFormatException => readNumber
    }



  def generateList(n: Int): ZIO[Any, Nothing, List[Int]] = 
    val l = (1 to n).toList.map(num => Random().nextInt())
    ZIO.succeed(l)

  val program: ZIO[Any, Nothing, Unit] = 
    for 
      number <- readNumber
      list <- generateList(number)
      _ <- Console.printLine(list).orDie
    yield ()


  val run = program