package example

import simulacrum._

trait Functor[F[_]] { self =>
    def map[A, B](fa: F[A])(f: A => B): F[B]

    def lift[A, B](f: A => B): F[A] => F[B] = fa => map(fa)(f)

    def as[A, B](fa: F[A])(b: => B): F[B] = map(fa)(_ => b)

    def void[A](fa: F[A]): F[Unit] = map(fa)(_ => ())

    def compose[G[_], X](implicit G: Functor[G]): Functor[Lambda[X => F[G[X]]]] =
        new Functor[Lambda[X => F[G[X]]]] {
            def map[A, B](fga: F[G[A]])(f: A => B): F[G[B]] = {
                self.map(fga)(ga => G.map(ga)(a => f(a)))
            }
        }
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