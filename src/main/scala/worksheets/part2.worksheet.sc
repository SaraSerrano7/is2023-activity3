import zio.Cause.Empty
import zio.Cause.Fail
import zio.Cause.Die
import zio.Cause.Interrupt
import zio.Cause.Stackless
import zio.Cause.Then
import zio.Cause.Both
import scala.util.Random
import zio.test.TestRandom
import zio.test.Gen
import scala.util.Random
import zio.Exit.Success
import zio.Exit.Failure
import java.io.IOException
import hello.* 
import part1.* 
import part2.*
import zio.*
import zio.test.*

import zio.*
import zio.Runtime.default.*
import zio.test.Assertion.*

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

//-------------------

val read1 = Console.readLine("enter number").flatMap(str => ZIO.attempt(str.toInt))
val read2 = read1.refineToOrDie[NumberFormatException]
val read3 = read2.orDie

val readAgain1 = read1.sandbox
val readAgain2 = readAgain1.exit
// val readAgain3 = read


val again1 = ZIO.succeed("3").flatMap(str => ZIO.attempt(str.toInt)).refineToOrDie[NumberFormatException]
val again2 = again1.sandbox.exit

//-----------------

val try0a = ZIO.succeed("3") 
val try0b = ZIO.succeed("aaaaaa") 
val try1a = try0a.flatMap(str => ZIO.attempt(str.toInt))
val try1b = try0b.flatMap(str => ZIO.attempt(str.toInt))
val try2 = try1b.catchAll{
    case _: NumberFormatException => try0a.flatMap(str => ZIO.attempt(str.toInt))
}
val try3 = try2.orDie.flatMap{n => 
    println(n)
    Console.printLine(n).orDie
}

//------------------
// Gen.string.flatMap(name => println(name))
// check(Gen.string) {
    // name => println(name)
// }


val myList = (1 to 5).toList
val myRandom = zio.Random.nextInt
myList.map(n => zio.Random.nextInt)

TestRandom.feedInts(1, 2, 3)
val n1 = zio.Random.nextInt

TestRandom.clearInts

val myList2 = List[Int]()

val what = 
    for 
        _ <- TestRandom.feedInts(1, 2)
        num <- zio.Random.nextInt
    yield (myList2.::(num))

TestRandom.clearInts
val myList3 = List[Int]()
val myNumlist = (1 to 5).toList

// zio.Random.nextIn

// def myFunc(n: Int) =
//     if n > 0 then
//          zio.Random.nextInt else

val myList4 = (1 to 5).toList.map(num => zio.Random.nextInt)

TestRandom.feedInts(1, 2)
for 
    num <- zio.Random.nextInt

// yield (myList3.::(num))
yield (num)

def myFunc(n: Int, intAcc: List[Int], zioAcc: ZIO[Any, Nothing, List[Int]]): ZIO[Any, Nothing, List[Int]] =
    if n > 0 then
        val result = 
            for 
                num <- zio.Random.nextInt
                li <- zioAcc
            yield (li.::(num)) //intAcc
        myFunc(n - 1, intAcc, result)
    else
        zioAcc


def myFunc2(n: Int, intAcc: List[Int]): ZIO[Any, Nothing, List[Int]] = 
    if n > 1 then
        for 
            num <- zio.Random.nextInt
            result <- myFunc2(n-1, intAcc) 
        yield( result.::(num))
         
        else    
            for 
                num <- zio.Random.nextInt
            yield (intAcc.::(num))

myFunc2(2, List[Int]())

// for 
//     n <- myNumlist
//     r <- zio.Random.nextInt
// yield (myList3.::(r))

// def myFunc(n: Int, acc: List[Int]) =
//     if n > 0 then



// TestRandom.feedInts(1, 2)
// (1 to 5).toList.foreach{
// //     myList3.::(zio.Random.nextInt)
//         // println
//     for 
//         num <- zio.Random.nextInt
//         _ <- myList3.::(num)
//     yield()
// }
// ZIO.succeed(myList3)

//----------------------

val f = ZIO.attempt("aaaa".toInt).orDie
val sand = f.sandbox
val exit0 = sand.exit
 val c = sand.catchAll(cause => cause match    
    case Fail(value, trace) => Console.printLine("value, trace: ", value, trace).orDie
    case Die(value, trace) => Console.printLine("value, trace: ", value, trace).orDie
    case _ => Console.printLine("nothing").orDie
 )



val whySuccess = ZIO.attempt("aaaaa".toInt)
val catching = whySuccess.orDie
val examine = ZIO.attempt(ZIO.attempt("aaaaa".toInt).orDie).catchAll{
    _ => ZIO.fail(())
}
val exit1 = examine.exit
exit1.isSuccess
assertZIO(exit1.isSuccess)(equalTo(true))

