package puritan

import scalaz.{ Equal, Order }
import scalaz.effect.IO

object Exit {
  def exitWith(code: ExitCode): IO[Nothing] = TODO
  def exitFailure: IO[Nothing] = TODO
  def exitSuccess: IO[Nothing] = TODO
}

case class ExitCode(code: Int)
object ExitSuccess extends ExitCode(0)
object ExitFail extends ExitCode(1)

object ExitCode {
  def ExitCodeHasEqual: Equal[ExitCode] = TODO
  def ExitCodeHasOrder: Order[ExitCode] = TODO
}

