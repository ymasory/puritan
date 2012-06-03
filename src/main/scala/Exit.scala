package puritan

import scalaz.{ Equal, Order }
import scalaz.effect.IO

object Exit {
  def exitWith(code: ExitCode): IO[Nothing] = TODO
  def exitFailure: IO[Nothing] = TODO
  def exitSuccess: IO[Nothing] = TODO
}

sealed trait ExitCode {
  def code: Int = this match {
    case ExitSuccess => 0
    case ExitFailure => 1
  }
}

case object ExitSuccess extends ExitCode
case object ExitFail extends ExitCode

object ExitCode {
  def ExitCodeHasEqual: Equal[ExitCode] = TODO
  def ExitCodeHasOrder: Order[ExitCode] = TODO
}

