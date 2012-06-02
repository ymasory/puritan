package puritan

/** A character set understood by Java. */
sealed abstract class Charset private[puritan](name: String)

/** Contains all the `Charset` instances. */
object Charsets {
  /** The UTF-8 character set, as understood by Java. */
  case object Utf8 extends Charset("utf-8")
  /** The UTF-16 character set, as understood by Java. */
  case object Utf16 extends Charset("utf-16")
}
