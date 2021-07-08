package example

trait Monad[F[_]] {
    def pure[A](a: A): F[A]
    def flatMap[A, B](ff: A => F[B])(fa: F[A]): F[B]
}

object Monad {
    implicit val listMonad: Monad[List] = new Monad[List] {
        def pure[A](a: A): List[A] = List(a)
        def flatMap[A, B](ff: A => List[B])(fa: List[A]): List[B] = fa.flatMap(ff)
    }

    implicit val optionMonad: Monad[Option] = new Monad[Option] {
        def pure[A](a: A): Option[A] = Option(a)
        def flatMap[A, B](ff: A => Option[B])(fa: Option[A]): Option[B] = fa.flatMap(ff)
    }
}