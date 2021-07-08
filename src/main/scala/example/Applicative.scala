package example

trait Applicative[F[_]] {
    def pure[A](a: A): F[A]    

    def apply[A, B](ff: F[A => B])(fa: F[A]): F[B]

    def map[A, B](f: A => B)(fa: F[A]): F[B] = apply(pure(f))(fa)

    def map2[A, B, Z](f: (A, B) => Z)(fa: F[A], fb: F[B]): F[Z] = 
        apply(map((b: B) => f(_, b))(fb))(fa)

    def map3[A, B, C, Z](f: (A, B, C) => Z)(fa: F[A], fb: F[B], fc: F[C]): F[Z] = 
        apply(map2((b: B, c: C) => f(_, b, c))(fb, fc))(fa)
}

object Applicative {
    implicit val listA: Applicative[List] = new Applicative[List] {
        def pure[A](a: A): List[A] = List(a)
        def apply[A, B](ff: List[A => B])(fa: List[A]): List[B] = 
            (fa zip ff) map {case (a,f) => f(a)}
    }

    implicit val optA: Applicative[Option] = new Applicative[Option] {
        def pure[A](a: A): Option[A] = Some(a)
        def apply[A, B](ff:Option[A => B])(fa: Option[A]): Option[B] = 
            { (fa, ff) match {
                case (None, None) => None
                case (None, _) => None
                case (Some(a), Some(f)) => Some(f(a))
            }}
    }
}