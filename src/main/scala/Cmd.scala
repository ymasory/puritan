package puritan

import scalaz.effect.IO

object Cmd {
  def system(cmd: String): IO[ExitCode] = TODO
  def rawSystem(cmd: String): IO[ExitCode] = TODO
}
