package puritan

import scalaz.effect.IO

/** Miscellaneous information about the system environment. */
object Environment {

  /** Retrieves the value of the environment variable `name`. */
  def variable(name: String): IO[String] = TODO

  /** Retrieves all environment variables as a map from variable names to
    * variable values. */
  def allVariables: IO[Map[String, String]] = TODO

  /** Retrieves the value of the JVM environment variable `name`. */
  def property(name: String): IO[String] = TODO

  /** Retrieves all JVM environment variables as a map from variable names to
    * variable values. */
  def allProperties: IO[Map[String, String]] = TODO
}

