package example

object TypeClass {
    def sumInts(ints: List[Int]) : Int = ints.foldRight(0)(_ + _)

    def concatStrings(strs: List[String]) : String = strs.foldRight("")(_ + _)

    def unionSets(sets: List[Set[String]]): Set[String] = sets.foldRight(Set.empty[String])(_ union _)

    def combineAll[A: Monoid](xs: List[A]): A = xs.foldRight(Monoid[A].empty)(Monoid[A].combine)
}

case class Pair[A, B](a: A, b: B)

trait Monoid[A] {
    def empty: A
    def combine(a: A, b: A): A
}

object Monoid {
    def apply[A: Monoid]: Monoid[A] = implicitly[Monoid[A]]

    implicit val intMonoid: Monoid[Int] = new Monoid[Int] {
        def empty = 0
        def combine(a: Int, b: Int): Int = a + b
    }

    implicit val stringMonoid: Monoid[String] = new Monoid[String] {
        def empty = ""
        def combine(a: String, b: String): String =  a concat b
    }

    implicit def pairMonoid[A, B](implicit ma: Monoid[A], mb: Monoid[B]): Monoid[Pair[A, B]] = new Monoid[Pair[A, B]] {
        def empty = Pair(ma.empty, mb.empty)
        def combine(a: Pair[A, B], b: Pair[A, B]): Pair[A, B] =  Pair(ma.combine(a.a, b.a), mb.combine(a.b, b.b))
    }
}