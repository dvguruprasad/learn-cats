import org.scalatest.funsuite._

import example._

class FunctorSpec extends AnyFunSuite {
    val listF = implicitly[Functor[List]]
    val optF = implicitly[Functor[Option]]

    test("map") {
        assert(listF.map(List(100, 200, 300))(_ / 100).equals(List(1, 2, 3)))
        assert(optF.map(Option(100))(_ / 100).equals(Option(1)))
    }

    test("lift") {
        val liftedOpt = optF.lift((x: Int) => x.toString)
        assert(liftedOpt(Option(100)).equals(Option("100")))

        val liftedList = listF.lift((x: Int) => x * 2)
        assert(liftedList(List(2, 4, 8)).equals(List(4, 8, 16)))
    }

    test("as") {
        assert(listF.as(List(1, 2, 3, 4))(2).equals(List(2, 2, 2, 2)))
    }

    test("compose") {
        val listOptFunctor = listF compose optF

        assert(listOptFunctor.map(List(Option(100), Option(200), Option(300)))(_ / 100)
            .equals(List(Option(1), Option(2), Option(3))))
    }
}