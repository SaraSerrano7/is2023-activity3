package part4

import zio.*
import java.io.IOException

object GuessGame extends ZIOAppDefault:

  val readNumber: ZIO[Any, Nothing, Int] =
    Console.readLine("Enter a maximum number to guess: ")
      .orDie
      .flatMap{
        str => ZIO.attempt(str.toInt)
      }
      .catchAll{
        case _: NumberFormatException => readNumber
      }
      .flatMap{
        num => if num < 1 then readNumber else ZIO.succeed(num)
      }

      

  def guessNumber(maximum: Int, secret: Int): ZIO[Any, Nothing, Int] =
    for 
      line <- Console.readLine(s"Enter a number between 1 and $maximum: ").orDie
      number <- ZIO.attempt(line.toInt).catchAll{case _: NumberFormatException => guessNumber(maximum, secret)}
      _ <- if number < 1  || number > maximum then
        guessNumber(maximum, secret)
        else if number < secret then
        badResult("target is HIGHER", maximum, secret)
        else if number > secret then
          badResult("target is LOWER", maximum, secret)
        else
          printMessage("Congratulations, you have guessed OK")
    yield (number)

  def badResult(message: String, maximum: Int, secret: Int): ZIO[Any, Nothing, Unit] =
    for 
      _ <- printMessage(message)
      _ <- guessNumber(maximum, secret)
    yield()

    
  def printMessage(message: String): ZIO[Any, Nothing, Unit] =
    for 
      _ <- Console.printLine(s"$message").orDie
    yield()

  val program: ZIO[Any, Nothing, Unit] = 
    for 
      maxNum <- readNumber
      secret <- Random.nextIntBetween(1, maxNum)
      _ <- guessNumber(maxNum, secret)
    yield ()

  val run = program
