package part1

import MyZIO.*
import zio.test.*
import zio.test.Assertion.*


object MyZIOSuite extends ZIOSpecDefault:

  val spec = suite("part1")(
    test("this succeed, zio succeed") {
      val step0This = 2
      val step0Zio = 3
      val step1This = MyZIO.succeed(step0This)
      val step1Zio = MyZIO.succeed(step0Zio)
      val step2 = step1This.orElse(step1Zio).run("")
      assertTrue(step2 == Right(step0This))
    },
    test("this succeed, zio fail") {
      val step0This = 2
      val step0Zio = 3
      val step1This = MyZIO.succeed(step0This)
      val step1Zio = MyZIO.fail(step0Zio)
      val step2 = step1This.orElse(step1Zio).run("")
      assertTrue(step2 == Right(step0This))
    },
    test("this fail, zio succeed") {
      val step0This = 2
      val step0Zio = 3
      val step1This = MyZIO.fail(step0This)
      val step1Zio = MyZIO.succeed(step0Zio)
      val step2 = step1This.orElse(step1Zio).run("")
      assertTrue(step2 == Right(step0Zio))
    },
    test("this fail, zio fail") {
      val step0This = 2
      val step0Zio = 3
      val step1This = MyZIO.fail(step0This)
      val step1Zio = MyZIO.fail(step0Zio)
      val step2 = step1This.orElse(step1Zio).run("")
      assertTrue(step2 == Left(step0Zio))
    }
  )
