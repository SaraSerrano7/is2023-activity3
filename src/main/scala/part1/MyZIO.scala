package part1

// Afegiu les variàncies correctes de MyZIO !!!

// final case class MyZIO[-R, +E, +A](run: R => Either[E, A]) {

final case class MyZIO[-R, +E, +A](run: R => Either[E, A]) {

  self =>

    def orElse[R1 <: R, E1 >: E, B >: A](zio: => MyZIO[R1, E1, B]): MyZIO[R1, E1, B] = 
     MyZIO{
      r => 
        this.run(r) match
          case Right(a) => Right(a) 
          case Left(e) => zio.run(r) 
        }
}

object MyZIO:
  def succeed[A](a: A): MyZIO[Any, Nothing, A] =
    MyZIO { _ => Right(a) }

  def fail[E](e: E): MyZIO[Any, E, Nothing] =
    MyZIO { _ => Left(e) }
