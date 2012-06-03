package puritan

import scalaz.Show
import scalaz.effect.IO

/** Access to stdout, stderr, stdin. */
trait Console {
  /** Read a single character from stdin */
  def readChar: IO[Char] = TODO
  /** Read a single line from stdin, without line terminators. */
  def readLine: IO[String] = TODO

  /** Print a single character to stdout. */
  def putChar: IO[Char] = TODO
  /** Print a string to stdout, without appending a newline character. */
  def putStr: IO[String] = TODO
  /** Print a string to stderr, without appending a newline character. */
  def ePutStr: IO[String] = TODO
  /** Print a string to stdout, and append a platform-dependent newline
   * character. */
  def putStrLn: IO[String] = TODO
  /** Print a string to stderr, and append a platform-dependent newline
   * character. */
  def ePutStrLn: IO[String] = TODO

  /** Print a single byte to stdout. */
  def putByte: IO[Byte] = TODO
  /** Print a single short to stdout. */
  def putShort: IO[Short] = TODO
  /** Print a single int to stdout. */
  def putInt: IO[Int] = TODO
  /** Print a single long to stdout. */
  def putLong: IO[Long] = TODO

  /** Print a float to stdout. */
  def putFloat: IO[Float] = TODO
  /** Print a float to stdout. */
  def putDouble: IO[Double] = TODO

  /** Print a float to stdout using `Show.shows`. */
  def putShow[A:Show]: IO[A] = TODO
}

/** Simply mixes in the [[puritan.Console]] trait for easier access. */
object Console extends Console

