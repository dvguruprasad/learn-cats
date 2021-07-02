package example

import simulacrum._

trait Functor[F[_]] {
    def map[A, B](fa: F[A])(f: A => B): F[B]
}

trait FunctorLaws {
    def identity[F[_], A](fa: F[A])(implicit F: Functor[F]): Boolean = F.map(fa)(a => a) == fa

    def composite[F[_], A, B, C](fa: F[A], ab: A => B, bc: B => C)(implicit F: Functor[F]): Boolean =
        F.map(F.map(fa)(ab))(bc) == F.map(fa)(ab andThen bc)
}

object Functor {
    implicit val listFunctor: Functor[List] = new Functor[List] {
        def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)
    }

    implicit val optFunctor: Functor[Option] = new Functor[Option] {
        def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)
    }

    implicit def function1Functor[X]: Functor[Function1[X, *]] = new Functor[Function1[X, *]] {
        def map[A, B](fa: X => A)(f: A => B): X => B = fa andThen f
    }
}