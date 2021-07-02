import org.scalatest.funsuite._

class HelloSpec extends AnyFunSuite {
    test("Hello should start with h") {
        assert("hello".startsWith("h"))
    }
}