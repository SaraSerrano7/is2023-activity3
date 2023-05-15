package part1

import MyZIO.*
import zio.test.*
import zio.test.Assertion.*


object MyZIOSuite extends ZIOSpecDefault:

//usar las cuatro combinaciones de succeed y fails
  val spec = suite("part1")(
    test("this succeed, alt succeed") {
      val step0a = 1 + 1
      val step0b = 1 + 2
      val step1a = MyZIO.succeed(step0a)
      val step1b = MyZIO.succeed(step1a)
      val step2 = step1a.orElse(step1b).run("")
      assertTrue(step2 == Right(step0a))
    },
    test("this succeed, alt fail") {
      val step0a = 1 + 1
      val step0b = 1 + 2
      val step1a = MyZIO.succeed(step0a)
      val step1b = MyZIO.fail(step1a)
      val step2 = step1a.orElse(step1b).run("")
      assertTrue(step2 == Right(step0a))
    },
    test("this fail, alt succeed") {
      val step0a = 1 + 1
      val step0b = 1 + 2
      val step1a = MyZIO.fail(step0a)
      val step1b = MyZIO.succeed(step0b)
      val step2 = step1a.orElse(step1b).run("")
      assertTrue(step2 == Right(step0b))
    },
    test("this fail, alt fail") {
      val step0a = 1 + 1
      val step0b = 1 + 2
      val step1a = MyZIO.fail(step0a)
      val step1b = MyZIO.fail(step0b)
      val step2 = step1a.orElse(step1b).run("")
      assertTrue(step2 == Left(step0b))
    }
  )
