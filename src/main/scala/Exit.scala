package puritan

import scalaz.{ Equal, Order }
import scalaz.effect.IO

object Exit {
  def exitWith(code: ExitCode): IO[Nothing] = TODO
  def exitFailure: IO[Nothing] = TODO
  def exitSuccess: IO[Nothing] = TODO
}

sealed trait ExitCode {
  def code: Int
}

object ExitSuccess extends ExitCode {
  def code = 0
}

object ExitFail extends ExitCode {
  def code = 1
}

object ExitCode {
  def ExitCodeHasEqual: Equal[ExitCode] = TODO
  def ExitCodeHasOrder: Order[ExitCode] = TODO
}

