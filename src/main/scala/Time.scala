package puritan

import scalaz.effect.IO

/** Access to current UNIX time. */
trait Time {
  def milliTime: IO[Long] = IO { System currentTimeMillis() }
  def nanoTime: IO[Long] = IO { System nanoTime() }
}

/** Simply mixes in the [[puritan.Time]] trait for easier access. */
object Time extends Time
