import zio.test.*

object AllTests extends ZIOSpecDefault:

  val spec =
    hello.HelloSuite.spec
      + part1.MyZIOSuite.spec
      + part2.RandomListSuite.spec
      + part3.RandomListSuite.spec
      + part4.GuessGameSuite.spec
