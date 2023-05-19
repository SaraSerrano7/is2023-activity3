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
        num => if num < 0 then readNumber else ZIO.succeed(num)
      }



  def printMessage(message: String): ZIO[Any, Nothing, Unit] =
    for 
      _ <- Console.printLine(s"$message").orDie
    yield()
    

  def badResult(message: String, secret: Int): ZIO[Any, Nothing, Unit] =
    for 
      // _ <- Console.printLine(s"$message").orDie
      _ <- printMessage(message)
      _ <- readPossibleNum(secret)
    yield()


  def readPossibleNum(secret: Int): ZIO[Any, Nothing, Unit] =
    for
      number <- readNumber 
      result <- 
        if number < secret then
          badResult("target is HIGHER", secret)
        else if number > secret then
          badResult("target is LOWER", secret)
        else
          printMessage("Congratulations, you have guessed OK")
    yield ()

  val program: ZIO[Any, Nothing, Unit] = 
    for 
      maxNum <- readNumber
      secret <- Random.nextIntBounded(maxNum)
      _ <- readPossibleNum(secret)
    yield ()




  /*
  1. Pedir un número al usuario
    1a. Si no es numero, volver a pedirlo (como la primera implementacion que hice)
  2. Calcular un numero random secreto entre 0 y el del usuario
  3. Pedir un numero al usuario entre 0 y su maximo
    3a. Si está out of scope, es una string, pedir otro
    3b. Sino, si es diferente del secreto:
      3ba. Si es mas grande, indicarlo
      3bb. Si es mas pequeño, indicarlo
    3c. Si es igual, indicar que ha ganado
  GOTO 3
  */

  val run = program
