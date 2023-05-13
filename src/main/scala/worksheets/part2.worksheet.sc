import scala.util.Random
import zio.Exit.Success
import zio.Exit.Failure
import java.io.IOException
import hello.* 
import part1.* 
import part2.*

import zio.*
import zio.Runtime.default.*

val line: ZIO[String, Throwable, String] = Console.readLine("Enter a number")
//nos dicen que no puede fallar
val lineOrDie = line.orDie
val parsedLine = lineOrDie.flatMap{
    str =>
        ZIO.attempt(str.toInt)
}
// refineToOrDie es para quedarnos con el defecto que queramos, y morir en cualquier otra cosa
val refinedLine = parsedLine.refineToOrDie[NumberFormatException]

val refine2 = parsedLine.orDie
// val refine3 = refine2.

val getANumber = 
    (for 
        input <- Console.readLine("Enter a number").orDie
        result <- ZIO
            .attempt(input.toInt)
            
    yield result)
        .refineToOrDie[IllegalArgumentException]

val getSandbox = getANumber.sandbox
val getExit = getSandbox.exit

val examineLine = parsedLine.sandbox
val examineLine2 = examineLine.unsandbox
  /*
    1. pedimos un entero al usuario
    11. el workflow nunca falla
    12. si detectamos que no es un entero --> defecto
    13. la aplicacion falla
    2. 
  */

val getANumber2 = Console.readLine.orDie.flatMap(str => ZIO.succeed(str.toInt))

val getFakeNumber = ZIO.succeed("3".toInt)

val getANumber3 = getFakeNumber.map(println(_))
    // getANumber2 match
    //     case Success(value) => value
    //     case Failure(cause) => println(s"cause=$cause")
    //     case _ => println("caca")

// val maybeResult = getFakeNumber.unsafeRun

// val readNumber: ZIO[Any, Nothing, Int] = 
//     Console.readLine.orDie

//--------------------------------

val l = (0 to 5).toList.map(n => Random().nextInt())

val zioList = ZIO.succeed(l)

l.map{
    n => println(n)
}