package part1

// Afegiu les variàncies correctes de MyZIO !!!

// final case class MyZIO[-R, +E, +A](run: R => Either[E, A]) {

final case class MyZIO[-R, +E, +A](run: R => Either[E, A]) {

  self =>

    // def flatMap[R1 <: R, E1 >: E, B](f: A => MyZIO[R1, E1, B]): MyZIO[R1, E1, B] =
    //   MyZIO {
    //     r => 
    //       this.run(r) match
    //         case Right(a) => f(a).run(r)
    //         case Left(e) => Left(e)  
    //   }


// def orElse[B](zio: => MyZIO[R, E, B]): MyZIO[R, E, B] = 
  // def orElse[R1 <: R, E, B >: A](zio: => MyZIO[R1, E, B]): MyZIO[R1, E, B] = 
    def orElse[R1 <: R, E1 >: E, B >: A](zio: => MyZIO[R1, E1, B]): MyZIO[R1, E1, B] = 
     MyZIO{
      r => 
        this.run(r) match
          case Right(a) => Right(a) 
          case Left(e) => zio.run(r) 
          // case Left(e) => zio.run(r) match
          //   case Left(e2) => Left(e2): Either[E, B]
          //   case Right(a2) => Right(a2): Either[E, B]
        }
 /*
  1. hay que retornar un MyZIO
  2. parametro del constructor: A partir del environtment r, ejecuta el calculo de either
  3. funcionamiento orElse: si this es some, se retorna this, sino se retorna la evaluación del default
  4. no usamos la anotacion sa @ porque necesitamos que coincidan los tipos
  */
}

  


object MyZIO:
  def succeed[A](a: A): MyZIO[Any, Nothing, A] =
    MyZIO { _ => Right(a) }

  def fail[E](e: E): MyZIO[Any, E, Nothing] =
    MyZIO { _ => Left(e) }
