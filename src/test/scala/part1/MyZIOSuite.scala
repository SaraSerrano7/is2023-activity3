package part1

import MyZIO.*
import zio.test.*
import zio.test.Assertion.*


object MyZIOSuite extends ZIOSpecDefault:

//usar las cuatro combinaciones de succeed y fails
  val spec = suite("part1")(
    test("orElse smart assertion") {
      ???
      // val result = MyZIO.succeed(1 +1).run
      // assertion(MyZIO.succeed(1+1))(equalTo(2))
      // for 
      //   result <- ??? //MyZIO.succeed(1 + 1) 
      // yield assertTrue(result.run == 2)
    }
    
  )
