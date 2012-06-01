import org.scalacheck._

object HandleTest extends Properties("Main") {
  import Prop.forAll

  property("startsWith") = forAll { (a: String, b: String) => 
    (a+b).startsWith(a)
  }

  property("endsWith") = forAll { (a: String, b: String) => 
    (a+b).endsWith(b)
  }
}
