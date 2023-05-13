package part1

// Afegiu les variàncies correctes de MyZIO !!!

// final case class MyZIO[-R, +E, +A](run: R => Either[E, A]) {

final case class MyZIO[-R, +E, +A](run: R => Either[E, A]) {

  self =>

  def orElse[R1 <: R, E2, B >: A](zio: => MyZIO[R1, E2, B]): MyZIO[R1, E2, B] = 
  /*
  1. hay que retornar un MyZIO
  2. parametro del constructor: A partir del environtment r, ejecuta el calculo de either
  3. funcionamiento orElse: si this es some, se retorna this, sino se retorna el default
  */

    MyZIO{
      r => 
        this.run(r) match
          case Right(a) => Right(a): Either[E2, B] //: Either[E, B]
          case Left(e) => zio.run(r) match
            case Left(e2) => Left(e2)
            case Right(a2) => Right(a2)   //: Either[E, B]
        }
        
}

  


object MyZIO:
  def succeed[A](a: A): MyZIO[Any, Nothing, A] =
    MyZIO { _ => Right(a) }

  def fail[E](e: E): MyZIO[Any, E, Nothing] =
    MyZIO { _ => Left(e) }
