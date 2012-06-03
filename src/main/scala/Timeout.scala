package puritan

import scalaz.Validation
import scalaz.effect.IO

object Timeout {
  def timeout[A](millis: Int)(act: IO[A]): IO[Validation[String, A]] = TODO
}
