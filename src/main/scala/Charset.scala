package puritan

import java.nio.charset.{ Charset => JCharset }

/** A character set understood by Java. */
sealed abstract class PuritanCharset private[puritan](val name: String) {
  def javaCharset: JCharset = TODO
}

/** Contains all the [[puritan.Charset]] instances. */
object PuritanCharsets {
  /** The UTF-8 character set, as understood by Java. */
  case object Utf8 extends PuritanCharset("utf-8")
  /** The UTF-16 character set, as understood by Java. */
  case object Utf16 extends PuritanCharset("utf-16")
}
