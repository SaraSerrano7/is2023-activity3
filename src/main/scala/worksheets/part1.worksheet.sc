import hello.* 
import part1.* 

import zio.*
import zio.test.*

val res1 = MyZIO.succeed(1 + 1)
val res11 = res1.run("")
val res111 = res1.run(_)
val res12 = res11.orElse(res11)

val res2 = Hello.run
val res3 = Hello

//-----------------------------

val step0a = 1 + 1
val step0b = 1 + 2
val step1b = MyZIO.succeed(step0b)
val step1a = MyZIO.succeed(step0a)
val step2 = step1a.run("")
// val step3 = step1a.orElse(step0b)
val step3a = step1a.orElse(step1b)
val step4a = step3a.run("")
val step5a = step4a == Right(step0a)

val fail1a = MyZIO.fail(step0a)
val fail1b = MyZIO.succeed(step0b)
val fail2 = fail1a.orElse(fail1b)
val fail3 = fail2.run("")