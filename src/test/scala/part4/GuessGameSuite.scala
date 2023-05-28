package part4

import zio.* 
import zio.test.*
import part4.GuessGame.readNumber
import part4.GuessGame
import part4.GuessGame.program

object GuessGameSuite extends ZIOSpecDefault:

  val spec =
    suite("part4")(
      test("readNumber: bad input"){
        for
          _ <- TestConsole.feedLines("patata", "\n", "0", "-23", "45")
          number <- TestConsole.silent(readNumber)
        yield assertTrue(number == 45)
    },
    test("guessNumber: reading bad numbers until winning game"){
      for
        _ <- TestRandom.feedInts(7)
        _ <- TestConsole.feedLines("patata", "\n", "0", "-23", "45", "23", "2", "10", "5", "6", "7")
        _ <- TestConsole.silent(program)
        output <- TestConsole.output
      yield assertTrue(check(output))
    }
  )


  def check(output: Vector[String]): Boolean =
    val cond1 = output.count(_ == "Enter a maximum number to guess: ") == 5
    val cond2 = output.count(str => str.contains("target is LOWER")) == 2
    val cond3 = output.count(str => str.contains("target is HIGHER")) == 3
    val cond4 = output.count(str => str.contains("Enter a number between 1 and")) == 6
    val cond5 = output.length == 17
    val cond6 = output.last.trim() == "Congratulations, you have guessed OK"
    cond1 && cond2 && cond3 && cond4 && cond5 && cond6