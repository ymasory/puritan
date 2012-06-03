package puritan

import scalaz.{ Equal, Order }
import scalaz.effect.IO

object Exit {
  def exitWith(code: ExitCode): IO[Nothing] = TODO
  def exitFailure: IO[Nothing] = TODO
  def exitSuccess: IO[Nothing] = TODO
}

final case class ExitCode(code: Int)

object ExitCode {
  val ExitSuccess = ExitCode(0)
  val ExitFail = ExitCode(1)

  def ExitCodeHasEqual: Equal[ExitCode] = TODO
  def ExitCodeHasOrder: Order[ExitCode] = TODO
}

